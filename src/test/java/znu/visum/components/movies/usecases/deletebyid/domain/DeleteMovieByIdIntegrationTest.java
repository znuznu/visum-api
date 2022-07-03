package znu.visum.components.movies.usecases.deletebyid.domain;

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
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.history.domain.ViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.reviews.domain.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class DeleteMovieByIdIntegrationTest {
  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private DeleteMovieById deleteMovieById;
  @Autowired private MovieQueryRepository movieQueryRepository;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private ViewingHistoryRepository viewingHistoryRepository;
  @Autowired private ActorRepository actorRepository;
  @Autowired private DirectorRepository directorRepository;
  @Autowired private GenreRepository genreRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAMovieIdThatDoesNotExist_whenTheMovieIsDeleted_itShouldThrowAnError() {
    Assertions.assertThatThrownBy(() -> deleteMovieById.process(1000L))
        .isInstanceOf(NoSuchMovieIdException.class);
  }

  @Test
  @Sql({
    "/sql/new/01__insert_actors.sql",
    "/sql/new/02__insert_directors.sql",
    "/sql/new/03__insert_genres.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/06__insert_cast_members.sql",
    "/sql/new/07__insert_reviews.sql",
    "/sql/new/08__insert_viewing_history.sql",
    "/sql/new/100__jt_movie_director.sql",
    "/sql/new/101__jt_movie_genre.sql",
  })
  void givenAMovieIdThatExists_whenTheMovieIsDeleted_itShouldDeleteTheMovieAndHisOrphans() {
    assertThat(movieQueryRepository.existsById(1L)).isTrue();
    assertThat(reviewRepository.existsById(1L)).isTrue();
    assertThat(viewingHistoryRepository.existsById(1L)).isTrue();
    assertThat(viewingHistoryRepository.existsById(5L)).isTrue();

    deleteMovieById.process(1L);

    assertThat(movieQueryRepository.findById(1L)).isNotPresent();
    assertThat(reviewRepository.findById(1L)).isNotPresent();
    assertThat(viewingHistoryRepository.findById(1L)).isNotPresent();
    assertThat(viewingHistoryRepository.findById(5L)).isNotPresent();

    // Should not be removed
    assertThat(actorRepository.existsById(1L)).isTrue();
    assertThat(actorRepository.existsById(2L)).isTrue();
    assertThat(directorRepository.existsById(4L)).isTrue();
    assertThat(genreRepository.existsById(1L)).isTrue();
    assertThat(genreRepository.existsById(3L)).isTrue();
    assertThat(genreRepository.existsById(4L)).isTrue();
  }
}
