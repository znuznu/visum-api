package znu.visum.components.reviews.usecases.create;

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
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.domain.ReviewRepository;
import znu.visum.components.reviews.usecases.create.domain.CreateReview;
import znu.visum.components.reviews.usecases.create.domain.CreateReviewCommand;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateReviewIntegrationTest")
@ActiveProfiles("flyway")
class CreateReviewIntegrationTest {

  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private CreateReview usecase;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenACommandWithAMovieIdThatDoesNotExist_whenTheReviewIsSaved_itShouldThrowAnError() {
    Assertions.assertThrows(NoSuchMovieIdException.class, () -> usecase.process(command(42L)));
  }

  @Test
  @Sql("/sql/insert_movie_with_review.sql")
  void givenAReviewWithAMovieIdThatAlreadyHaveAReview_whenTheReviewIsSaved_itShouldThrowAnError() {
    Assertions.assertThrows(
        MaximumMovieReviewsReachedException.class, () -> usecase.process(command(30L)));
  }

  @Test
  @Sql("/sql/insert_single_movie.sql")
  void givenAReviewWithAMovieIdThatExists_whenTheReviewIsSaved_itShouldSaveTheReview() {
    assertThat(movieRepository.findById(1L).get().getReview()).isNull();

    Review review = usecase.process(command(1L));

    assertThat(movieRepository.findById(1L).get().getReview()).isNotNull();
    assertThat(reviewRepository.findById(review.getId())).isPresent();
  }

  private CreateReviewCommand command(long movieId) {
    return CreateReviewCommand.builder()
        .content("Bla bla bla. \n Blo blo blo. \n Wow !")
        .grade(7)
        .movieId(movieId)
        .build();
  }
}
