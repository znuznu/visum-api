package znu.visum.components.reviews.usecases.update.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDateTime;

@Schema(description = "Represents a review updated.")
public class UpdateMovieReviewResponse {
  @Schema(description = "The identifier of the review updated.")
  private final long id;

  @Schema(description = "The grade of the review updated.")
  private final int grade;

  @Schema(description = "The text of the review updated.")
  private final String content;

  @Schema(description = "The identifier of the movie for which was updated the review.")
  private final long movieId;

  @Schema(description = "The creation date of the review updated.")
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  public UpdateMovieReviewResponse(
      long id, int grade, String content, long movieId, LocalDateTime creationDate) {
    this.id = id;
    this.grade = grade;
    this.content = content;
    this.movieId = movieId;
    this.creationDate = creationDate;
  }

  public static UpdateMovieReviewResponse from(Review review) {
    return new UpdateMovieReviewResponse(
        review.getId(),
        review.getGrade(),
        review.getContent(),
        review.getMovie().getId(),
        review.getCreationDate());
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

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  public LocalDateTime getCreationDate() {
    return creationDate;
  }
}
