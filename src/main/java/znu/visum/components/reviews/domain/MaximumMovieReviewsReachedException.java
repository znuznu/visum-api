package znu.visum.components.reviews.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

public class MaximumMovieReviewsReachedException extends VisumException {

  private MaximumMovieReviewsReachedException(long id) {
    super(
        String.format(
            "The maximum number of reviews for the movie with id %s has been reached.", id),
        VisumExceptionStatus.BAD_REQUEST,
        "MAXIMUM_NUMBER_OF_REVIEWS_REACHED");
  }

  public static MaximumMovieReviewsReachedException withId(long id) {
    return new MaximumMovieReviewsReachedException(id);
  }
}
