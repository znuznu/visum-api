package znu.visum.components.reviews.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class MaximumMovieReviewsReachedException extends VisumException {
  public MaximumMovieReviewsReachedException(long id) {
    super(
        String.format(
            "The maximum number of reviews for the movie with id %s has been reached.", id));
  }
}
