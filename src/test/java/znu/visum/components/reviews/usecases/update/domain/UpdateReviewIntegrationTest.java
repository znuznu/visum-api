package znu.visum.components.reviews.usecases.update.domain;

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
import znu.visum.components.reviews.domain.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class UpdateReviewIntegrationTest {

  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private UpdateReview updateReview;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private MovieQueryRepository movieQueryRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAReviewIdThatDoesNotExist_whenTheReviewIsUpdated_itShouldThrowAnError() {
    Assertions.assertThatThrownBy(() -> updateReview.process(command(42L)))
        .isInstanceOf(NoSuchReviewIdException.class);
  }

  @Test
  @Sql({
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/07__insert_reviews.sql"
  })
  void givenAReviewThatExists_itShouldUpdateTheReview() {
    updateReview.process(command(1L));

    Movie movie = movieQueryRepository.findById(1L).get();
    // No side effects
    assertThat(movie.getTitle()).isEqualTo("Fake movie 1");

    assertThat(movie.getReview().getGrade()).isEqualTo(Grade.of(7));
    assertThat(movie.getReview().getContent()).isEqualTo("Bla bla bla. \n Blo blo blo. \n Wow !");

    Review reviewUpdated = reviewRepository.findById(1L).get();
    assertThat(reviewUpdated.getGrade()).isEqualTo(Grade.of(7));
    assertThat(reviewUpdated.getContent())
        .isEqualTo(Content.of("Bla bla bla. \n Blo blo blo. \n Wow !"));
    assertThat(reviewUpdated.getCreationDate())
        .isEqualTo(LocalDateTime.of(2021, 10, 26, 15, 54, 33));
  }

  private UpdateReviewCommand command(long id) {
    return UpdateReviewCommand.builder()
        .id(id)
        .content("Bla bla bla. \n Blo blo blo. \n Wow !")
        .grade(7)
        .build();
  }
}
