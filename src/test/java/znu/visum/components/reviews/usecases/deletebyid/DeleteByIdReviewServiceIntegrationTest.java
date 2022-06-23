package znu.visum.components.reviews.usecases.deletebyid;

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
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.reviews.domain.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.ReviewRepository;
import znu.visum.components.reviews.usecases.deletebyid.domain.DeleteByIdMovieReviewService;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DeleteByIdReviewServiceIntegrationTest")
@ActiveProfiles("flyway")
class DeleteByIdReviewServiceIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private DeleteByIdMovieReviewService service;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAReviewIdThatDoesNotExist_whenTheReviewIsDeleted_itShouldThrowAnError() {
    Assertions.assertThrows(NoSuchReviewIdException.class, () -> service.deleteById(1000L));
  }

  @Test
  @Sql("/sql/insert_movie_with_review.sql")
  void givenAReviewIdThatExists_whenTheReviewIsDeleted_itShouldDeleteTheReviewButNotTheMovie() {
    reviewRepository.deleteById(1L);

    assertThat(reviewRepository.findById(1L)).isNotPresent();
    assertThat(movieRepository.findById(30L)).isPresent();
  }
}
