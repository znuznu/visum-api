package znu.visum.components.history.usecases.deletebyid;

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
import znu.visum.components.history.domain.MovieViewingHistoryRepository;
import znu.visum.components.history.domain.NoSuchViewingHistoryException;
import znu.visum.components.history.usecases.deletebyid.domain.DeleteByIdMovieViewingHistoryService;
import znu.visum.components.movies.domain.MovieRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DeleteByIdMovieViewingHistoryServiceIntegrationTest")
@ActiveProfiles("flyway")
class DeleteByIdMovieViewingHistoryServiceIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private DeleteByIdMovieViewingHistoryService service;
  @Autowired private MovieRepository movieRepository;
  @Autowired private MovieViewingHistoryRepository movieViewingHistoryRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void
      givenAViewingHistoryIdThatDoesNotExist_whenTheViewingHistoryIsDeleted_itShouldThrowAnError() {
    Assertions.assertThrows(NoSuchViewingHistoryException.class, () -> service.deleteById(1000L));
  }

  @Test
  @Sql("/sql/insert_movie_with_viewing_history.sql")
  void
      givenAViewingHistoryIdThatExists_whenTheViewingHistoryIsDeleted_itShouldDeleteTheViewingHistoryButNotTheMovie() {
    movieViewingHistoryRepository.deleteById(1L);

    assertThat(movieViewingHistoryRepository.findById(1L)).isNotPresent();
    assertThat(movieRepository.findById(1L)).isPresent();
  }
}
