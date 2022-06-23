package znu.visum.components.reviews.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.ports.ReviewRepository;

@Service
public class DeleteByIdMovieReviewService {
  private final ReviewRepository reviewRepository;

  @Autowired
  public DeleteByIdMovieReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public void deleteById(long id) {
    if (reviewRepository.findById(id).isEmpty()) {
      throw new NoSuchReviewIdException(Long.toString(id));
    }

    reviewRepository.deleteById(id);
  }
}
