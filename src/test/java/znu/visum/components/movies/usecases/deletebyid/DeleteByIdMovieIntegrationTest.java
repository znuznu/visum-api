package znu.visum.components.movies.usecases.deletebyid;

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
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.movies.usecases.deletebyid.domain.DeleteByIdMovie;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.reviews.domain.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DeleteByIdMovieServiceIntegrationTest")
@ActiveProfiles("flyway")
class DeleteByIdMovieIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private DeleteByIdMovie deleteByIdMovie;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private MovieViewingHistoryRepository movieViewingHistoryRepository;
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
    Assertions.assertThrows(NoSuchMovieIdException.class, () -> deleteByIdMovie.process(1000L));
  }

  @Test
  @Sql(
      scripts = {
        "/sql/insert_cast_and_genres.sql",
        "/sql/insert_movie_with_review_and_viewing_history_and_metadata.sql"
      })
  void givenAMovieIdThatExists_whenTheMovieIsDeleted_itShouldDeleteTheMovieAndHisOrphans() {
    deleteByIdMovie.process(3L);

    assertThat(movieRepository.findById(3L)).isNotPresent();
    assertThat(reviewRepository.findById(1L)).isNotPresent();
    assertThat(movieViewingHistoryRepository.findById(1L)).isNotPresent();
    assertThat(movieViewingHistoryRepository.findById(2L)).isNotPresent();
    assertThat(actorRepository.findById(1L)).isPresent();
    assertThat(actorRepository.findById(2L)).isPresent();
    assertThat(directorRepository.findById(1L)).isPresent();
    assertThat(genreRepository.findById(1L)).isPresent();
    assertThat(genreRepository.findById(3L)).isPresent();
  }
}
