package znu.visum.components.reviews.usecases.update.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("Represents a review to update.")
public class UpdateMovieReviewRequest {
  @ApiModelProperty("The grade of the review to update.")
  @Min(0)
  @Max(10)
  @NotNull
  private int grade;

  @ApiModelProperty("The text of the review to update.")
  @NotNull
  @NotEmpty
  private String content;

  @JsonCreator
  public UpdateMovieReviewRequest(
      @JsonProperty("grade") int grade, @JsonProperty("content") String content) {
    this.grade = grade;
    this.content = content;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
