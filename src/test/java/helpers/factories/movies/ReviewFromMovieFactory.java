package helpers.factories.movies;

import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDateTime;

public enum ReviewFromMovieFactory {
  INSTANCE;

  private ReviewFromMovie createReviewFromMovie(Long id) {
    return ReviewFromMovie.builder()
        .id(id)
        .grade(new Grade(10))
        .content("Some text.")
        .creationDate(LocalDateTime.of(2000, 1, 1, 0, 0))
        .updateDate(LocalDateTime.of(2000, 1, 1, 0, 0))
        .movieId(1L)
        .build();
  }

  public ReviewFromMovie getWithId(Long id) {
    return createReviewFromMovie(id);
  }
}
