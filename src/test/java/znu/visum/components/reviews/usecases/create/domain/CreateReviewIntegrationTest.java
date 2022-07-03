package znu.visum.components.reviews.usecases.create.domain;

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
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.domain.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class CreateReviewIntegrationTest {

  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private CreateReview createReview;
  @Autowired private MovieQueryRepository movieQueryRepository;
  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenACommandWithAMovieIdThatDoesNotExist_whenTheReviewIsSaved_itShouldThrowAnError() {
    assertThatThrownBy(() -> createReview.process(command(42L)))
        .isInstanceOf(NoSuchMovieIdException.class);
  }

  @Test
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/07__insert_reviews.sql",
  })
  void givenAReviewWithAMovieIdThatAlreadyHaveAReview_whenTheReviewIsSaved_itShouldThrowAnError() {
    assertThatThrownBy(() -> createReview.process(command(1L)))
        .isInstanceOf(MaximumMovieReviewsReachedException.class);
  }

  @Test
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  void givenAReviewWithAMovieIdThatExists_whenTheReviewIsSaved_itShouldSaveTheReview() {
    assertThat(movieQueryRepository.findById(1L).get().getReview()).isNull();

    Review review = createReview.process(command(1L));

    assertThat(movieQueryRepository.findById(1L).get().getReview()).isNotNull();
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
