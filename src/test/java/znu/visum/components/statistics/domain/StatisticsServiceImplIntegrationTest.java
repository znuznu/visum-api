package znu.visum.components.statistics.domain;

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
import znu.visum.components.statistics.domain.services.StatisticsServiceImpl;
import znu.visum.core.models.domain.Pair;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("StatisticsServiceImplIntegrationTest")
@ActiveProfiles("flyway")
class StatisticsServiceImplIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private StatisticsServiceImpl service;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @DisplayName(
      "getMovieCountOfTheYear() - it should return the count of all movies released in 2014")
  @Sql(
      scripts = {
        "/sql/truncate_movie_table.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  public void itShouldReturnTheCountOfAllMovieReleasedIn2014() {
    long count = this.service.getMovieCountOfTheYear(Year.of(2014));

    assertThat(count).isEqualTo(4);
  }

  @Test
  @DisplayName(
      "getReviewCountOfTheYear() - it should return the count of all reviews last updated in 2015")
  @Sql(
      scripts = {
        "/sql/truncate_movie_table.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql"
      })
  public void itShouldReturnTheCountOfAllReviewsLastUpdatedIn2015() {
    long count = this.service.getReviewCountOfTheYear(Year.of(2015));

    assertThat(count).isEqualTo(3);
  }

  @Test
  @DisplayName(
      "getNumberOfMoviesOfTheYearPerGenre() - it should return a list containing all genre and count of 2001")
  @Sql(
      scripts = {
        "/sql/truncate_movie_table.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
        "/sql/insert_multiple_movie_genres.sql"
      })
  public void itShouldReturnTheListOfAllGenreAndCountOf2001() {
    List<Pair<String, Integer>> countPerGenre =
        this.service.getNumberOfMoviesOfTheYearPerGenre(Year.of(2001));

    assertThat(countPerGenre)
        .usingRecursiveFieldByFieldElementComparator()
        .containsOnly(new Pair<>("Horror", 1), new Pair<>("Animation", 1));
  }

  // TODO We're covered by the it from the movie/review repos, but if a bug happened,
  // all methods should be tested here
}
