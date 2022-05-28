package znu.visum.components.reviews.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Schema(description = "Represents a review to create.")
public class CreateReviewRequest {
  @Schema(description = "The grade of the review to create.")
  @NotNull
  @Positive
  @Max(10)
  private final int grade;

  @Schema(description = "The text of the review to create.")
  @NotNull
  @NotEmpty
  private final String content;

  @Schema(description = "The identifier of the movie for which to create the review.")
  @NotNull
  private final long movieId;

  @JsonCreator
  public CreateReviewRequest(
      @JsonProperty("grade") int grade,
      @JsonProperty("content") String content,
      @JsonProperty("movieId") long movieId) {
    this.grade = grade;
    this.content = content;
    this.movieId = movieId;
  }
}
