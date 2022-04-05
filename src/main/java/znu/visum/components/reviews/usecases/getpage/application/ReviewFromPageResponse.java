package znu.visum.components.reviews.usecases.getpage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

  public ReviewFromPageResponse(
      long id,
      int grade,
      String content,
      ResponseMovie movie,
      LocalDateTime creationDate,
      LocalDateTime updateDate) {
    this.id = id;
    this.grade = grade;
    this.content = content;
    this.movie = movie;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
  }

  public static ReviewFromPageResponse from(Review review) {
    return new ReviewFromPageResponse(
        review.getId(),
        review.getGrade(),
        review.getContent(),
        ResponseMovie.from(review.getMovie()),
        review.getCreationDate(),
        review.getUpdateDate());
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

  public ResponseMovie getMovie() {
    return movie;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public static class ResponseMovie {
    private final long id;

    private final String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate releaseDate;

    private final ResponseMovieMetadata metadata;

    public ResponseMovie(
        long id, String title, LocalDate releaseDate, ResponseMovieMetadata metadata) {
      this.id = id;
      this.title = title;
      this.releaseDate = releaseDate;
      this.metadata = metadata;
    }

    public static ResponseMovie from(MovieFromReview movieFromReview) {
      return new ResponseMovie(
          movieFromReview.getId(),
          movieFromReview.getTitle(),
          movieFromReview.getReleaseDate(),
          ResponseMovieMetadata.from(movieFromReview.getMetadata()));
    }

    public long getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public LocalDate getReleaseDate() {
      return releaseDate;
    }

    public ResponseMovieMetadata getMetadata() {
      return metadata;
    }

    public static class ResponseMovieMetadata {
      @Schema(description = "The movie's poster URL.")
      private String posterUrl;

      public ResponseMovieMetadata() {}

      public static ResponseMovieMetadata from(
          MovieFromReview.MovieFromReviewMetadata movieMetadata) {
        return new Builder().posterUrl(movieMetadata.getPosterUrl()).build();
      }

      public String getPosterUrl() {
        return posterUrl;
      }

      public static class Builder {
        private final ResponseMovieMetadata metadata;

        public Builder() {
          this.metadata = new ResponseMovieMetadata();
        }

        public Builder posterUrl(String posterUrl) {
          this.metadata.posterUrl = posterUrl;
          return this;
        }

        public ResponseMovieMetadata build() {
          return this.metadata;
        }
      }
    }
  }
}
