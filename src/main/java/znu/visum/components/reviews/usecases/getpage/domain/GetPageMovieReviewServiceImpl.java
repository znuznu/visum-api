package znu.visum.components.reviews.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageMovieReviewServiceImpl implements GetPageMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public GetPageMovieReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public VisumPage<Review> findPage(int limit, int offset, Sort sort, String search) {
    return reviewRepository.findPage(limit, offset, sort, search);
  }
}
