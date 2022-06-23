package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.reviews.domain.Review;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class ReviewFromMovie {

  private Long id;
  private String content;
  private LocalDateTime updateDate;
  private LocalDateTime creationDate;
  private int grade;
  private Long movieId;

  public static ReviewFromMovie from(Review review) {
    return new ReviewFromMovie(
        review.getId(),
        review.getContent(),
        review.getUpdateDate(),
        review.getCreationDate(),
        review.getGrade(),
        review.getMovie().getId());
  }
}
