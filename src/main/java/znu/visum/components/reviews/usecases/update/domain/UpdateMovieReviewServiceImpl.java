package znu.visum.components.reviews.usecases.update.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;

@Service
public class UpdateMovieReviewServiceImpl implements UpdateMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public UpdateMovieReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Review update(Review newReview) {
    Review reviewToUpdate =
        reviewRepository
            .findById(newReview.getId())
            .orElseThrow(() -> new NoSuchReviewIdException(Long.toString(newReview.getId())));

    reviewToUpdate.setContent(newReview.getContent());
    reviewToUpdate.setGrade(newReview.getGrade());
    reviewToUpdate.setUpdateDate(null);

    return reviewRepository.save(reviewToUpdate);
  }
}
