package znu.visum.components.reviews.usecases.create.domain;

import znu.visum.components.reviews.domain.models.Review;

public interface CreateReviewService {
  Review save(Review review);
}
