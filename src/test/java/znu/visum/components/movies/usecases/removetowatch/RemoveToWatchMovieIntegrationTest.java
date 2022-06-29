package znu.visum.components.movies.usecases.removetowatch;

import org.junit.jupiter.api.Assertions;
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
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.movies.usecases.removetowatch.domain.RemoveToWatchMovie;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("MarkAsFavoriteServiceIntegrationTest")
@ActiveProfiles("flyway")
class RemoveToWatchMovieIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private RemoveToWatchMovie service;

  @Autowired private MovieRepository movieRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @DisplayName("when the movie does not exists it should throw")
  void givenAMovieIdThatDoesNotExist_itShouldThrowAnError() {
    Assertions.assertThrows(NoSuchMovieIdException.class, () -> service.process(1000L));
  }

  @Test
  @DisplayName(
      "when the movie exists and is already not a one to watch, it should not affect it and return false")
  @Sql("/sql/insert_movie_with_metadata.sql")
  void givenAMovieThatExists_whenTheMovieIsAlreadyNotMarked_itShouldNotAffectTheMovie() {
    boolean hasChanged = service.process(90L);

    assertThat(hasChanged).isFalse();

    Movie movie = movieRepository.findById(90L).get();

    assertThat(movie.isToWatch()).isFalse();

    // No side effect
    assertThat(movie.isFavorite()).isFalse();
    assertThat(movie.getTitle()).isEqualTo("Fake movie with metadata");
  }

  @Test
  @DisplayName(
      "when the movie exists and is a one to watch, it should mark it as a one not to watch and return true")
  @Sql("/sql/insert_movie_with_metadata_with_favorite_with_should_watch.sql")
  void givenAMovieThatExists_whenTheMovieIsMarked_itShouldRemoveTheFavorite() {
    boolean hasChanged = service.process(91L);

    assertThat(hasChanged).isTrue();

    Movie movie = movieRepository.findById(91L).get();

    assertThat(movie.isToWatch()).isFalse();

    // No side effect
    assertThat(movie.isFavorite()).isTrue();
    assertThat(movie.getTitle()).isEqualTo("Fake movie with metadata 91");
  }
}