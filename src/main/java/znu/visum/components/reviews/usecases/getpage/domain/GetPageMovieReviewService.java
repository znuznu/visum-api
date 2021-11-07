package znu.visum.components.reviews.usecases.getpage.domain;

import znu.visum.components.reviews.domain.models.Review;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

public interface GetPageMovieReviewService {
  VisumPage<Review> findPage(PageSearch<Review> page);
}
