package znu.visum.components.reviews.usecases.update.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.reviews.domain.*;

@Service
public class UpdateReview {

  private final ReviewRepository reviewRepository;

  @Autowired
  public UpdateReview(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public Review process(UpdateReviewCommand command) {
    Review review =
        reviewRepository
            .findById(command.getId())
            .orElseThrow(() -> NoSuchReviewIdException.with(Long.toString(command.getId())));

    var updatedReview =
        Review.builder()
            .id(review.getId())
            .grade(Grade.of(command.getGrade()))
            .content(Content.of(command.getContent()))
            .movie(review.getMovie())
            .creationDate(review.getCreationDate())
            .updateDate(null)
            .build();

    return reviewRepository.save(updatedReview);
  }
}
