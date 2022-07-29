package znu.visum.components.movies.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.movies.domain.DiaryFilters;
import znu.visum.components.movies.domain.MovieDiaryFragment;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.statistics.domain.AverageRating;
import znu.visum.components.statistics.domain.DateRange;
import znu.visum.components.statistics.domain.StatisticsMovie;
import znu.visum.core.models.common.Limit;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class PostgresMovieQueryRepositoryIntegrationTest {

  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private MovieQueryRepository movieQueryRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - no movies")
  @Test
  void whenNoMoviesExists_itShouldReturnAnEmptyList() {
    var dateRange = new DateRange(LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1));
    List<StatisticsMovie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(dateRange, new Limit(5));
    assertThat(movies).isEmpty();
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - limit inferior to the number of movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void whenMoreMoviesThanTheLimitExists_itShouldReturnAListWithTheLengthOfTheLimit() {
    var dateRange = new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2004, 1, 1));
    List<StatisticsMovie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(dateRange, new Limit(2));

    assertThat(movies)
        .hasSize(2)
        .extracting(StatisticsMovie::getId)
        .containsExactlyInAnyOrder(1L, 3L);
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - limit higher than the number of movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void whenLessMoviesThanTheLimitExists_itShouldReturnMoviesFound() {
    var dateRange = new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2003, 1, 1));
    List<StatisticsMovie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(dateRange, new Limit(10));

    assertThat(movies).hasSize(2).extracting(StatisticsMovie::getId).containsExactly(1L, 2L);
  }

  @DisplayName("getTotalRunningHoursBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheTotalRunningHours() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));
    int totalRunningHours = this.movieQueryRepository.getTotalRunningHoursBetween(dateRange);

    assertThat(totalRunningHours).isEqualTo(18);
  }

  @DisplayName("getTotalRunningHoursBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnZero() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));
    int totalRunningHours = this.movieQueryRepository.getTotalRunningHoursBetween(dateRange);

    assertThat(totalRunningHours).isZero();
  }

  @DisplayName("getMovieCountPerOriginalLanguageBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheMoviesCountPerOriginalLanguageOrderedByDescCount() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2015, 1));
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getMovieCountPerOriginalLanguageBetween(dateRange);

    assertThat(pairs)
        .containsOnly(
            new Pair<>("en", 4), new Pair<>("de", 2), new Pair<>("jp", 2), new Pair<>("uk", 2));
  }

  @DisplayName("getMovieCountPerOriginalLanguageBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyPerOriginalLanguageList() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2015, 1));
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getMovieCountPerOriginalLanguageBetween(dateRange);

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getMovieCountPerYearBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheMoviesCountPerYearOrderedByDescCount() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));
    List<Pair<Year, Integer>> pairs =
        this.movieQueryRepository.getMovieCountPerYearBetween(dateRange);

    assertThat(pairs)
        .containsOnly(
            new Pair<>(Year.of(2014), 4),
            new Pair<>(Year.of(2007), 2),
            new Pair<>(Year.of(2003), 2));
  }

  @DisplayName("getMovieCountPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyPerYearList() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));
    List<Pair<Year, Integer>> pairs =
        this.movieQueryRepository.getMovieCountPerYearBetween(dateRange);

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getMovieCountPerGenreBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
        "/sql/insert_multiple_movie_genres.sql"
      })
  void itShouldReturnTheMoviesCountPerGenreOrderedByDescCount() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2020, 1));
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getMovieCountPerGenreBetween(dateRange);

    assertThat(pairs)
        .containsOnly(
            new Pair<>("Horror", 5), new Pair<>("Animation", 3), new Pair<>("Biography", 1));
  }

  @DisplayName("getAverageMovieRatingPerYearBetween() - all movies with review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnTheAverageRatedMoviesOrderedByDescYear() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));
    List<Pair<Year, AverageRating>> pairs =
        this.movieQueryRepository.getAverageMovieRatingPerYearBetween(dateRange);

    assertThat(pairs)
        .containsOnly(
            new Pair<>(Year.of(2003), new AverageRating(9.0f)),
            new Pair<>(Year.of(2007), new AverageRating(5.0f)),
            new Pair<>(Year.of(2014), new AverageRating(3.25f)));
  }

  @DisplayName("getAverageMovieRatingPerYearBetween() - multiple movies with one without review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldAvoidTheMovieWithoutReviewAndReturnTheAverageRatedMoviesOrderedByDescYear() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2015, 1), LocalDate.ofYearDay(2016, 1));
    List<Pair<Year, AverageRating>> pairs =
        this.movieQueryRepository.getAverageMovieRatingPerYearBetween(dateRange);

    assertThat(pairs).containsOnly(new Pair<>(Year.of(2015), new AverageRating(4.0f)));
  }

  @DisplayName("getAverageMovieRatingPerYearBetween() - one movie with no review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnAnEmptyList() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));
    List<Pair<Year, AverageRating>> pairs =
        this.movieQueryRepository.getAverageMovieRatingPerYearBetween(dateRange);

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getAverageMovieRatingPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyAveragePerYearList() {
    var dateRange = new DateRange(LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));
    List<Pair<Year, AverageRating>> pairs =
        this.movieQueryRepository.getAverageMovieRatingPerYearBetween(dateRange);

    assertThat(pairs).isEmpty();
  }

  @DisplayName("findHighestRatedDuringYearsOlderMovies() - no movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyHighestRatedDuringYearsOlderMoviesList() {
    List<StatisticsMovie> movies =
        this.movieQueryRepository.findHighestRatedDuringYearOlderMovies(Year.of(2015));

    assertThat(movies).isEmpty();
  }

  @DisplayName("findHighestRatedDuringYearsOlderMovies() - with movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnHighestRatedDuringYearOlderMovies() {
    List<StatisticsMovie> movies =
        this.movieQueryRepository.findHighestRatedDuringYearOlderMovies(Year.of(2015));

    assertThat(movies).hasSize(2).extracting(StatisticsMovie::getId).containsExactly(8L, 14L);
  }

  @DisplayName("countByReleaseYear() - with movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnCountOfAllMoviesFrom2014() {
    long count = this.movieQueryRepository.countByReleaseYear(Year.of(2014));

    assertThat(count).isEqualTo(4);
  }

  @DisplayName("findByDiaryFilters() - no movies for the specified year")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_movie_with_viewing_history.sql",
      })
  void whenThereIsNoMovies_itShouldReturnNone() {
    var filters = DiaryFilters.builder().year(Year.of(2014)).build();
    List<MovieDiaryFragment> movies = this.movieQueryRepository.findByDiaryFilters(filters);

    assertThat(movies).isEmpty();
  }

  @DisplayName("findByDiaryFilters() - movies for the specified year, no filters")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_movies_viewing_year.sql",
      })
  void givenYear_whenThereIsMovies_itShouldReturnMoviesSeenDuringYear() {
    var filters = DiaryFilters.builder().year(Year.of(2019)).build();
    List<MovieDiaryFragment> movies = this.movieQueryRepository.findByDiaryFilters(filters);

    assertThat(movies).hasSize(3);
  }

  @DisplayName("findByDiaryFilters() - movies for the specified year, grade")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_movies_viewing_year.sql",
      })
  void givenYearGrade_whenThereIsMovies_itShouldReturnMoviesSeenDuringYearAndGrade() {
    var filters = DiaryFilters.builder().year(Year.of(2019)).grade(9).build();
    List<MovieDiaryFragment> movies = this.movieQueryRepository.findByDiaryFilters(filters);

    assertThat(movies).hasSize(2).extracting(MovieDiaryFragment::getId).containsExactly(1L, 15L);
  }

  @DisplayName("findByDiaryFilters() - movies for the specified year, grade, genreId")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_movies_viewing_year.sql",
      })
  void givenYearGradeGenreId_whenThereIsMovies_itShouldReturnMoviesSeenDuringYearGradeGenreId() {
    var filters = DiaryFilters.builder().year(Year.of(2019)).grade(9).genreId(3L).build();
    List<MovieDiaryFragment> movies = this.movieQueryRepository.findByDiaryFilters(filters);

    assertThat(movies).hasSize(1).extracting(MovieDiaryFragment::getId).containsExactly(1L);
  }
}
