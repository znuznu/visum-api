package znu.visum.components.history.usecases.deletebyid.domain;

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
import znu.visum.components.history.domain.NoSuchViewingHistoryException;
import znu.visum.components.history.domain.ViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class DeleteViewingHistoryByIdIntegrationTest {
  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private DeleteViewingHistoryById deleteViewingHistoryById;
  @Autowired private MovieQueryRepository movieQueryRepository;
  @Autowired private ViewingHistoryRepository viewingHistoryRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAViewingHistoryIdThatDoesNotExist_itShouldThrowAnError() {
    assertThatThrownBy(() -> deleteViewingHistoryById.process(1000L))
        .isInstanceOf(NoSuchViewingHistoryException.class);
  }

  @Test
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/08__insert_viewing_history.sql",
  })
  void givenAViewingHistoryIdThatExists_itShouldDeleteTheViewingHistoryButNotTheMovie() {
    viewingHistoryRepository.deleteById(1L);

    assertThat(viewingHistoryRepository.findById(1L)).isNotPresent();
    assertThat(movieQueryRepository.findById(1L)).isPresent();
  }
}
