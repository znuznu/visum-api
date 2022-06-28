package znu.visum.components.reviews.usecases.update;

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
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.reviews.domain.*;
import znu.visum.components.reviews.usecases.update.domain.UpdateReview;
import znu.visum.components.reviews.usecases.update.domain.UpdateReviewCommand;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("UpdateReviewIntegrationTest")
@ActiveProfiles("flyway")
class UpdateReviewIntegrationTest {

  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private UpdateReview service;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private MovieRepository movieRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void givenAReviewIdThatDoesNotExist_whenTheReviewIsUpdated_itShouldThrowAnError() {
    Assertions.assertThrows(NoSuchReviewIdException.class, () -> service.process(command(42L)));
  }

  @Test
  @Sql("/sql/insert_movie_with_review.sql")
  void givenAReviewThatExists_whenTheReviewIsUpdated_itShouldUpdateTheReview() {
    service.process(command(1L));

    Movie movie = movieRepository.findById(30L).get();

    // No side effect
    assertThat(movie.getTitle()).isEqualTo("Fake movie with review");

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
