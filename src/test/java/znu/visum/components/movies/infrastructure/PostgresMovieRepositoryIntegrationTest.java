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
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.MovieMetadata;
import znu.visum.components.movies.domain.models.ReviewFromMovie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.core.models.domain.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("PostgresMovieRepositoryIntegrationTest")
@ActiveProfiles("flyway")
public class PostgresMovieRepositoryIntegrationTest {

  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private MovieRepository movieRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - no movies")
  @Test
  public void whenNoMoviesExists_itShouldReturnAnEmptyList() {
    List<Movie> movies =
        this.movieRepository.findHighestRatedMoviesReleasedBetween(
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
  public void whenMoreMoviesThanTheLimitExists_itShouldReturnAListWithTheLengthOfTheLimit() {
    List<Movie> movies =
        this.movieRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.of(2000, 1, 1), LocalDate.of(2004, 1, 1), 2);
    assertThat(movies)
        .usingRecursiveFieldByFieldElementComparator()
        .containsOnly(
            new Movie.Builder()
                .id(1L)
                .title("Fake movie 1")
                .releaseDate(LocalDate.of(2001, 10, 12))
                .favorite(true)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2021, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(1L)
                        .grade(9)
                        .content("Some text 1")
                        .movieId(1L)
                        .creationDate(LocalDateTime.of(2021, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2021, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt1111")
                        .tmdbId(1111L)
                        .movieId(1L)
                        .budget(1000)
                        .revenue(10000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 1")
                        .runtime(111)
                        .originalLanguage("en")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(1L)
                            .movieId(1L)
                            .viewingDate(LocalDate.of(2020, 1, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(2L)
                            .movieId(1L)
                            .viewingDate(LocalDate.of(2020, 1, 2))
                            .build()))
                .build(),
            new Movie.Builder()
                .id(3L)
                .title("Fake movie 3")
                .releaseDate(LocalDate.of(2003, 10, 12))
                .favorite(false)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2021, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(3L)
                        .grade(10)
                        .content("Some text 3")
                        .movieId(3L)
                        .creationDate(LocalDateTime.of(2021, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2021, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt3333")
                        .tmdbId(3333L)
                        .movieId(3L)
                        .budget(3000)
                        .revenue(30000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 3")
                        .runtime(333)
                        .originalLanguage("en")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(5L)
                            .movieId(3L)
                            .viewingDate(LocalDate.of(2020, 4, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(6L)
                            .movieId(3L)
                            .viewingDate(LocalDate.of(2020, 5, 2))
                            .build()))
                .build());
  }

  @DisplayName("findHighestRatedMoviesReleasedBetween() - limit higher than the number of movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  public void whenLessMoviesThanTheLimitExists_itShouldReturnMoviesFound() {
    List<Movie> movies =
        this.movieRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.of(2000, 1, 1), LocalDate.of(2003, 1, 1), 10);
    assertThat(movies)
        .usingRecursiveFieldByFieldElementComparator()
        .containsOnly(
            new Movie.Builder()
                .id(1L)
                .title("Fake movie 1")
                .releaseDate(LocalDate.of(2001, 10, 12))
                .favorite(true)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2021, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(1L)
                        .grade(9)
                        .content("Some text 1")
                        .movieId(1L)
                        .creationDate(LocalDateTime.of(2021, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2021, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt1111")
                        .tmdbId(1111L)
                        .movieId(1L)
                        .budget(1000)
                        .revenue(10000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 1")
                        .runtime(111)
                        .originalLanguage("en")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(1L)
                            .movieId(1L)
                            .viewingDate(LocalDate.of(2020, 1, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(2L)
                            .movieId(1L)
                            .viewingDate(LocalDate.of(2020, 1, 2))
                            .build()))
                .build(),
            new Movie.Builder()
                .id(2L)
                .title("Fake movie 2")
                .releaseDate(LocalDate.of(2002, 10, 12))
                .favorite(true)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2021, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(2L)
                        .grade(1)
                        .content("Some text 2")
                        .movieId(2L)
                        .creationDate(LocalDateTime.of(2021, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2021, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt2222")
                        .tmdbId(2222L)
                        .movieId(2L)
                        .budget(2000)
                        .revenue(20000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 2")
                        .runtime(222)
                        .originalLanguage("en")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(3L)
                            .movieId(2L)
                            .viewingDate(LocalDate.of(2020, 2, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(4L)
                            .movieId(2L)
                            .viewingDate(LocalDate.of(2020, 2, 2))
                            .build()))
                .build());
  }

  @DisplayName("getTotalRunningHoursBetween()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  public void itShouldReturnTheTotalRunningHours() {
    int totalRunningHours =
        this.movieRepository.getTotalRunningHoursBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));

    assertThat(totalRunningHours).isEqualTo(18);
  }

  @DisplayName("getTotalRunningHoursBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  public void whenThereIsNoMovies_itShouldReturnZero() {
    int totalRunningHours =
        this.movieRepository.getTotalRunningHoursBetween(
            LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2004, 1));

    assertThat(totalRunningHours).isEqualTo(0);
  }

  @DisplayName("getNumberOfMoviesPerOriginalLanguage()")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  public void itShouldReturnTheMoviesCountPerOriginalLanguageOrderedByDescCount() {
    List<Pair<String, Integer>> pairs =
        this.movieRepository.getNumberOfMoviesPerOriginalLanguageBetween(
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
  public void whenThereIsNoMovies_itShouldReturnAnEmptyPerOriginalLanguageList() {
    List<Pair<String, Integer>> pairs =
        this.movieRepository.getNumberOfMoviesPerOriginalLanguageBetween(
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
  public void itShouldReturnTheMoviesCountPerYearOrderedByDescCount() {
    List<Pair<Integer, Integer>> pairs =
        this.movieRepository.getNumberOfMoviesPerYearBetween(
            LocalDate.ofYearDay(2003, 1), LocalDate.ofYearDay(2015, 1));

    assertThat(pairs).containsOnly(new Pair<>(2014, 4), new Pair<>(2007, 2), new Pair<>(2003, 2));
  }

  @DisplayName("getNumberOfMoviesPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  public void whenThereIsNoMovies_itShouldReturnAnEmptyPerYearList() {
    List<Pair<Integer, Integer>> pairs =
        this.movieRepository.getNumberOfMoviesPerYearBetween(
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
  public void itShouldReturnTheMoviesCountPerGenreOrderedByDescCount() {
    List<Pair<String, Integer>> pairs =
        this.movieRepository.getNumberOfMoviesPerGenreBetween(
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
  public void itShouldReturnTheAverageRatedMoviesOrderedByDescYear() {
    List<Pair<Integer, Float>> pairs =
        this.movieRepository.getRatedMoviesAveragePerYearBetween(
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
  public void itShouldAvoidTheMovieWithoutReviewAndReturnTheAverageRatedMoviesOrderedByDescYear() {
    List<Pair<Integer, Float>> pairs =
        this.movieRepository.getRatedMoviesAveragePerYearBetween(
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
  public void itShouldReturnAnEmptyList() {
    List<Pair<Integer, Float>> pairs =
        this.movieRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("getRatedAverageMoviesPerYearBetween() - empty result")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  public void whenThereIsNoMovies_itShouldReturnAnEmptyAveragePerYearList() {
    List<Pair<Integer, Float>> pairs =
        this.movieRepository.getRatedMoviesAveragePerYearBetween(
            LocalDate.ofYearDay(2015, 2), LocalDate.ofYearDay(2016, 1));

    assertThat(pairs).isEmpty();
  }

  @DisplayName("findHighestRatedDuringYearsOlderMovies() - no movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  public void whenThereIsNoMovies_itShouldReturnAnEmptyHighestRatedDuringYearsOlderMoviesList() {
    List<Movie> movies = this.movieRepository.findHighestRatedDuringYearOlderMovies(Year.of(2015));

    assertThat(movies).isEmpty();
  }

  @DisplayName("findHighestRatedDuringYearsOlderMovies() - with movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  public void itShouldReturnHighestRatedDuringYearOlderMovies() {
    List<Movie> movies = this.movieRepository.findHighestRatedDuringYearOlderMovies(Year.of(2015));

    assertThat(movies)
        .usingRecursiveFieldByFieldElementComparator()
        .containsOnly(
            new Movie.Builder()
                .id(8L)
                .title("Fake movie 8")
                .releaseDate(LocalDate.of(2007, 10, 12))
                .favorite(true)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2015, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(8L)
                        .grade(6)
                        .content("Some text 8")
                        .movieId(8L)
                        .creationDate(LocalDateTime.of(2015, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2015, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt8888")
                        .tmdbId(8888L)
                        .movieId(8L)
                        .budget(8000)
                        .revenue(80000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 8")
                        .runtime(888)
                        .originalLanguage("de")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(100L)
                            .movieId(8L)
                            .viewingDate(LocalDate.of(2020, 5, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(101L)
                            .movieId(8L)
                            .viewingDate(LocalDate.of(2020, 6, 2))
                            .build()))
                .build(),
            new Movie.Builder()
                .id(14L)
                .title("Fake movie 14")
                .releaseDate(LocalDate.of(2014, 10, 12))
                .favorite(false)
                .toWatch(false)
                .creationDate(LocalDateTime.of(2015, 10, 20, 15, 54, 33))
                .actors(new ArrayList<>())
                .directors(new ArrayList<>())
                .genres(new ArrayList<>())
                .review(
                    new ReviewFromMovie.Builder()
                        .id(14L)
                        .grade(1)
                        .content("Some text 14")
                        .movieId(14L)
                        .creationDate(LocalDateTime.of(2015, 10, 26, 15, 54, 33))
                        .updateDate(LocalDateTime.of(2015, 10, 27, 15, 54, 33))
                        .build())
                .metadata(
                    new MovieMetadata.Builder()
                        .imdbId("tt1414")
                        .tmdbId(1414L)
                        .movieId(14L)
                        .budget(14000)
                        .revenue(140000)
                        .tagline("A tagline!")
                        .overview("An overview!")
                        .posterUrl("An URL 14")
                        .runtime(141)
                        .originalLanguage("jp")
                        .build())
                .viewingDates(
                    List.of(
                        new MovieViewingHistory.Builder()
                            .id(9L)
                            .movieId(14L)
                            .viewingDate(LocalDate.of(2020, 7, 1))
                            .build(),
                        new MovieViewingHistory.Builder()
                            .id(10L)
                            .movieId(14L)
                            .viewingDate(LocalDate.of(2020, 8, 2))
                            .build()))
                .build());
  }

  @DisplayName("countAllByReleaseDateYear() - with movies")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  public void itShouldReturnCountOfAllMoviesFrom2014() {
    long count = this.movieRepository.countAllByReleaseDateYear(Year.of(2014));

    assertThat(count).isEqualTo(4);
  }
}
