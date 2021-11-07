package znu.visum.components.reviews.usecases.create.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.reviews.domain.models.Review;

@ApiModel("Represents the review created.")
public class CreateReviewResponse {
  @ApiModelProperty("The identifier of the review created.")
  private final long id;

  @ApiModelProperty("The grade of the review created.")
  private final int grade;

  @ApiModelProperty("The text of the review created.")
  private final String content;

  @ApiModelProperty("The identifier of the movie for which the review has been created.")
  private final long movieId;

  public CreateReviewResponse(long id, int grade, String content, long movieId) {
    this.id = id;
    this.grade = grade;
    this.content = content;
    this.movieId = movieId;
  }

  public static CreateReviewResponse from(Review review) {
    return new CreateReviewResponse(
        review.getId(), review.getGrade(), review.getContent(), review.getMovie().getId());
  }

  public long getId() {
    return id;
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
