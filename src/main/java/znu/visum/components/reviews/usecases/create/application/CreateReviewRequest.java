package znu.visum.components.reviews.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel("Represents a review to create.")
public class CreateReviewRequest {
  @ApiModelProperty("The grade of the review to create.")
  @NotNull
  @Positive
  @Max(10)
  private final int grade;

  @ApiModelProperty("The text of the review to create.")
  @NotNull
  @NotEmpty
  private final String content;

  @ApiModelProperty("The identifier of the movie for which to create the review.")
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

  public int getGrade() {
    return grade;
  }

  public String getContent() {
    return content;
  }

  public long getMovieId() {
    return movieId;
  }
}
