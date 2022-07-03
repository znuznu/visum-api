package znu.visum.components.movies.usecases.markastowatch.domain;

import org.assertj.core.api.Assertions;
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
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

import static org.assertj.core.api.Assertions.assertThat;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class MarkMovieAsToWatchIntegrationTest {
  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private MarkMovieAsToWatch markMovieAsToWatch;

  @Autowired private MovieQueryRepository movieQueryRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAMovieIdThatDoesNotExist_itShouldThrowAnError() {
    Assertions.assertThatThrownBy(() -> markMovieAsToWatch.process(1000L))
        .isInstanceOf(NoSuchMovieIdException.class);
  }

  @Test
  @Sql({"/sql/new/04__insert_movies.sql", "/sql/new/05__insert_movie_metadata.sql"})
  void givenAMovieThatExists_whenTheMovieIsNotAlreadyMarked_itShouldMarkTheMovieAsToWatch() {
    markMovieAsToWatch.process(1L);

    Movie movie = movieQueryRepository.findById(1L).get();

    assertThat(movie.isToWatch()).isTrue();

    // No side effect
    assertThat(movie.isFavorite()).isFalse();
    assertThat(movie.getTitle()).isEqualTo("Fake movie 1");
  }
}
