package znu.visum.components.reviews.usecases.update.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.NoSuchReviewIdException;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.domain.ReviewRepository;

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

    var reviewToSave =
        Review.builder()
            .id(reviewToUpdate.getId())
            .content(newReview.getContent())
            .grade(newReview.getGrade())
            .creationDate(reviewToUpdate.getCreationDate())
            .movie(reviewToUpdate.getMovie())
            .updateDate(null)
            .build();

    return reviewRepository.save(reviewToSave);
  }
}
