package znu.visum.components.reviews.usecases.deprecated.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;

@Service
public class GetByIdMovieReviewServiceImpl implements GetByIdMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public GetByIdMovieReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Review findById(long id) {
    return reviewRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchReviewIdException(Long.toString(id)));
  }
}
