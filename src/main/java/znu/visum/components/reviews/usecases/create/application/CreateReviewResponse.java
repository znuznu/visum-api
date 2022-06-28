package znu.visum.components.reviews.usecases.create.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.reviews.domain.Review;

@AllArgsConstructor
@Getter
@Schema(description = "Represents the review created.")
public class CreateReviewResponse {
  @Schema(description = "The identifier of the review created.")
  private final long id;

  @Schema(description = "The grade of the review created.")
  private final int grade;

  @Schema(description = "The text of the review created.")
  private final String content;

  @Schema(description = "The identifier of the movie for which the review has been created.")
  private final long movieId;

  public static CreateReviewResponse from(Review review) {
    return new CreateReviewResponse(
        review.getId(),
        review.getGrade().getValue(),
        review.getContent().getText(),
        review.getMovie().getId());
  }
}
