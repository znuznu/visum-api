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
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieDiaryFragment;
import znu.visum.components.movies.domain.MovieQueryRepository;
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
    List<Movie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1), 5);
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
    List<Movie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.of(2000, 1, 1), LocalDate.of(2004, 1, 1), 2);

    assertThat(movies).hasSize(2).extracting(Movie::getId).containsExactlyInAnyOrder(1L, 3L);
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - limit higher than the number of movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void whenLessMoviesThanTheLimitExists_itShouldReturnMoviesFound() {
    List<Movie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.of(2000, 1, 1), LocalDate.of(2003, 1, 1), 10);

    assertThat(movies).hasSize(2).extracting(Movie::getId).containsExactly(1L, 2L);
  }

  @DisplayName("getTotalRunningHoursBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheTotalRunningHours() {
    int totalRunningHours =
        this.movieQueryRepository.getTotalRunningHoursBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));

    assertThat(totalRunningHours).isEqualTo(18);
  }

  @DisplayName("getTotalRunningHoursBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnZero() {
    int totalRunningHours =
        this.movieQueryRepository.getTotalRunningHoursBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));

    assertThat(totalRunningHours).isZero();
  }

  @DisplayName("getNumberOfMoviesPerOriginalLanguage()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheMoviesCountPerOriginalLanguageOrderedByDescCount() {
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getNumberOfMoviesPerOriginalLanguageBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs)
        .containsOnly(
            new Pair<>("en", 4), new Pair<>("de", 2), new Pair<>("jp", 2), new Pair<>("uk", 2));
  }

  @DisplayName("getNumberOfMoviesPerOriginalLanguage() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyPerOriginalLanguageList() {
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getNumberOfMoviesPerOriginalLanguageBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getNumberOfMoviesPerYearBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  void itShouldReturnTheMoviesCountPerYearOrderedByDescCount() {
    List<Pair<Integer, Integer>> pairs =
        this.movieQueryRepository.getNumberOfMoviesPerYearBetween(
            LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs).containsOnly(new Pair<>(2014, 4), new Pair<>(2007, 2), new Pair<>(2003, 2));
  }

  @DisplayName("getNumberOfMoviesPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyPerYearList() {
    List<Pair<Integer, Integer>> pairs =
        this.movieQueryRepository.getNumberOfMoviesPerYearBetween(
            LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getNumberOfMoviesPerGenreBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
        "/sql/insert_multiple_movie_genres.sql"
      })
  void itShouldReturnTheMoviesCountPerGenreOrderedByDescCount() {
    List<Pair<String, Integer>> pairs =
        this.movieQueryRepository.getNumberOfMoviesPerGenreBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2020, 1));

    assertThat(pairs)
        .containsOnly(
            new Pair<>("Horror", 5), new Pair<>("Animation", 3), new Pair<>("Biography", 1));
  }

  @DisplayName("getRatedAverageMoviesPerYearBetween() - all movies with review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnTheAverageRatedMoviesOrderedByDescYear() {
    List<Pair<Integer, Float>> pairs =
        this.movieQueryRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs)
        .containsOnly(new Pair<>(2003, 9.0f), new Pair<>(2007, 5.0f), new Pair<>(2014, 3.25f));
  }

  @DisplayName("getRatedAverageMoviesPerYearBetween() - multiple movies with one without review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldAvoidTheMovieWithoutReviewAndReturnTheAverageRatedMoviesOrderedByDescYear() {
    List<Pair<Integer, Float>> pairs =
        this.movieQueryRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2015, 1), LocalDate.ofYearDay(2016, 1));

    assertThat(pairs).containsOnly(new Pair<>(2015, 4.0f));
  }

  @DisplayName("getRatedAverageMoviesPerYearBetween() - one movie with no review")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnAnEmptyList() {
    List<Pair<Integer, Float>> pairs =
        this.movieQueryRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getRatedAverageMoviesPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyAveragePerYearList() {
    List<Pair<Integer, Float>> pairs =
        this.movieQueryRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("findHighestRatedDuringYearsOlderMovies() - no movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void whenThereIsNoMovies_itShouldReturnAnEmptyHighestRatedDuringYearsOlderMoviesList() {
    List<Movie> movies =
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
    List<Movie> movies =
        this.movieQueryRepository.findHighestRatedDuringYearOlderMovies(Year.of(2015));

    assertThat(movies).hasSize(2).extracting(Movie::getId).containsExactly(8L, 14L);
  }

  @DisplayName("countAllByReleaseDateYear() - with movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnCountOfAllMoviesFrom2014() {
    long count = this.movieQueryRepository.countAllByReleaseDateYear(Year.of(2014));

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
