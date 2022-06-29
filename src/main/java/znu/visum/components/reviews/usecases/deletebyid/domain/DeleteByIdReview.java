package znu.visum.components.reviews.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.ReviewRepository;

@Service
public class DeleteByIdReview {

  private final ReviewRepository reviewRepository;

  @Autowired
  public DeleteByIdReview(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public void deleteById(long id) {
    if (reviewRepository.findById(id).isEmpty()) {
      throw new NoSuchReviewIdException(Long.toString(id));
    }

    reviewRepository.deleteById(id);
  }
}
