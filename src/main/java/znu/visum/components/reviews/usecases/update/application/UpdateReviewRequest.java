package znu.visum.components.reviews.usecases.update.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Schema(description = "Represents a review to update.")
public class UpdateReviewRequest {

  @Schema(description = "The grade of the review to update.")
  @Min(0)
  @Max(10)
  @NotNull
  private final Integer grade;

  @Schema(description = "The text of the review to update.")
  @NotNull
  @NotEmpty
  private final String content;

  @JsonCreator
  public UpdateReviewRequest(
      @JsonProperty("grade") Integer grade, @JsonProperty("content") String content) {
    this.grade = grade;
    this.content = content;
  }
}
