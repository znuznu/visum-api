package znu.visum.components.reviews.usecases.deletebyid.domain;

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
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.reviews.domain.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class DeleteReviewByIdIntegrationTest {
  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private DeleteReviewById deleteReviewById;
  @Autowired private MovieQueryRepository movieQueryRepository;
  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAReviewIdThatDoesNotExist_whenTheReviewIsDeleted_itShouldThrowAnError() {
    Assertions.assertThatThrownBy(() -> deleteReviewById.deleteById(1000L))
        .isInstanceOf(NoSuchReviewIdException.class);
  }

  @Test
  @Sql({
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/07__insert_reviews.sql",
  })
  void givenAReviewIdThatExists_itShouldDeleteTheReviewButNotTheMovie() {
    reviewRepository.deleteById(1L);

    assertThat(reviewRepository.existsById(1L)).isFalse();
    assertThat(movieQueryRepository.existsById(1L)).isTrue();
  }
}
