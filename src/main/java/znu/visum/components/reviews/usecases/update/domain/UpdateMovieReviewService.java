package znu.visum.components.reviews.usecases.update.domain;

import znu.visum.components.reviews.domain.models.Review;

public interface UpdateMovieReviewService {
  Review update(Review review);
}
