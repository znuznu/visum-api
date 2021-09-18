package znu.visum.components.reviews.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Service
public class GetPageMovieReviewServiceImpl implements GetPageMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public GetPageMovieReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public VisumPage<Review> findPage(PageSearch<Review> pageSearch) {
    return reviewRepository.findPage(pageSearch);
  }
}
