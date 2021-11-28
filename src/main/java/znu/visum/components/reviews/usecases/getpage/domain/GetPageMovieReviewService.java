package znu.visum.components.reviews.usecases.getpage.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetPageMovieReviewService {
  VisumPage<Review> findPage(int limit, int offset, Sort sort, String search);
}
