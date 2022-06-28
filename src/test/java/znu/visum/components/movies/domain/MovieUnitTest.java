package znu.visum.components.movies.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;

import java.util.ArrayList;

class MovieUnitTest {

  @Test
  @DisplayName("When a review is added to a movie that already has a review, it should throw")
  void shouldThrowIfAlreadyHaveReview() {
    Movie movie =
        Movie.builder()
            .id(12L)
            .review(ReviewFromMovie.builder().build())
            .metadata(MovieMetadata.builder().build())
            .actors(new ArrayList<>())
            .build();

    Assertions.assertThatThrownBy(() -> movie.addReview(ReviewFromMovie.builder().build()))
        .isInstanceOf(MaximumMovieReviewsReachedException.class);
  }
}
