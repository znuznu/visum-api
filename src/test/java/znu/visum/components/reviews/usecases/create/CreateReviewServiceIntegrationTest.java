package znu.visum.components.reviews.usecases.create;

import helpers.factories.reviews.ReviewFactory;
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
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.reviews.domain.errors.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.components.reviews.usecases.create.domain.CreateReviewService;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateReviewServiceIntegrationTest")
@ActiveProfiles("flyway")
public class CreateReviewServiceIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private CreateReviewService service;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  public void givenAReviewWithAMovieIdThatDoesNotExist_whenTheReviewIsSaved_itShouldThrowAnError() {
    Assertions.assertThrows(
        NoSuchMovieIdException.class,
        () -> service.save(ReviewFactory.INSTANCE.getOneToSaveWithMovieId(42L)));
  }

  @Test
  @Sql("/sql/insert_movie_with_review.sql")
  public void
      givenAReviewWithAMovieIdThatAlreadyHaveAReview_whenTheReviewIsSaved_itShouldThrowAnError() {
    Assertions.assertThrows(
        MaximumMovieReviewsReachedException.class,
        () -> service.save(ReviewFactory.INSTANCE.getOneToSaveWithMovieId(30L)));
  }

  @Test
  @Sql("/sql/insert_single_movie.sql")
  public void givenAReviewWithAMovieIdThatExists_whenTheReviewIsSaved_itShouldSaveTheReview() {
    assertThat(movieRepository.findById(1L).get().getReview()).isNull();

    Review review = service.save(ReviewFactory.INSTANCE.getOneToSaveWithMovieId(1L));

    assertThat(movieRepository.findById(1L).get().getReview()).isNotNull();
    assertThat(reviewRepository.findById(review.getId())).isPresent();
  }
}
