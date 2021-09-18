package helpers.factories.reviews;

import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum ReviewFactory {
  INSTANCE;

  private Review createReview(long id, long movieId) {
    return new Review(
        id,
        "Bla bla bla. \n Blo blo blo. \n Wow !",
        LocalDateTime.of(2021, 12, 12, 7, 10),
        LocalDateTime.of(2021, 12, 12, 5, 10),
        7,
        new MovieFromReview(movieId, "Fake movie", LocalDate.of(2021, 6, 12)));
  }

  private Review createReviewToSave(long movieId) {
    return new Review(
        null,
        "Bla bla bla. \n Blo blo blo. \n Wow !",
        null,
        null,
        7,
        new MovieFromReview(movieId, null, null));
  }

  public Review getOneWithIdAndMovieId(long id, long movieId) {
    return createReview(id, movieId);
  }

  public Review getOneToSaveWithMovieId(long movieId) {
    return createReviewToSave(movieId);
  }
}
