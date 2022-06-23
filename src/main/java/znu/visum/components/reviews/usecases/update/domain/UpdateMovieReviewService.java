package znu.visum.components.reviews.usecases.update.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;

@Service
public class UpdateMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public UpdateMovieReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

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
