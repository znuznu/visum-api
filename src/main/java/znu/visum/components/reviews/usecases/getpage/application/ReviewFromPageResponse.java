package znu.visum.components.reviews.usecases.getpage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.reviews.domain.MovieFromReview;
import znu.visum.components.reviews.domain.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a review from a page.")
public class ReviewFromPageResponse {
  @Schema(description = "The identifier of the review.")
  private final long id;

  @Schema(description = "The grade of the review.")
  private final int grade;

  @Schema(description = "The text of the review.")
  private final String content;

  @Schema(description = "The movie for which the review was written.")
  private final ResponseMovie movie;

  @Schema(description = "The creation date of the review.")
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  @Schema(description = "The last update date of the review.")
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime updateDate;

  public static ReviewFromPageResponse from(Review review) {
    return new ReviewFromPageResponse(
        review.getId(),
        review.getGrade(),
        review.getContent(),
        ResponseMovie.from(review.getMovie()),
        review.getCreationDate(),
        review.getUpdateDate());
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovie {

    private final long id;
    private final String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate releaseDate;

    private final ResponseMovieMetadata metadata;

    public static ResponseMovie from(MovieFromReview movieFromReview) {
      return new ResponseMovie(
          movieFromReview.getId(),
          movieFromReview.getTitle(),
          movieFromReview.getReleaseDate(),
          ResponseMovieMetadata.from(movieFromReview.getMetadata()));
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class ResponseMovieMetadata {
      @Schema(description = "The movie's poster URL.")
      private String posterUrl;

      public static ResponseMovieMetadata from(
          MovieFromReview.MovieFromReviewMetadata movieMetadata) {
        return ResponseMovieMetadata.builder().posterUrl(movieMetadata.getPosterUrl()).build();
      }
    }
  }
}
