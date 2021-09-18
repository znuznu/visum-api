package znu.visum.components.reviews.usecases.deprecated.getbyid.domain;

import znu.visum.components.reviews.domain.models.Review;

public interface GetByIdMovieReviewService {
  Review findById(long id);
}
