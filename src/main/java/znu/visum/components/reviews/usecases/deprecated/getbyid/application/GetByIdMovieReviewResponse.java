package znu.visum.components.reviews.usecases.deprecated.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class GetByIdMovieReviewResponse {
  private final long id;

  private final int grade;

  private final String content;

  private final ResponseMovie movie;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime updateDate;

  public GetByIdMovieReviewResponse(
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

  public static GetByIdMovieReviewResponse from(Review review) {
    return new GetByIdMovieReviewResponse(
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

    public ResponseMovie(long id, String title, LocalDate releaseDate) {
      this.id = id;
      this.title = title;
      this.releaseDate = releaseDate;
    }

    public static ResponseMovie from(MovieFromReview movieFromReview) {
      return new ResponseMovie(
          movieFromReview.getId(), movieFromReview.getTitle(), movieFromReview.getReleaseDate());
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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ResponseMovie that = (ResponseMovie) o;
      return getId() == that.getId()
          && getTitle().equals(that.getTitle())
          && getReleaseDate().equals(that.getReleaseDate());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getId(), getTitle(), getReleaseDate());
    }
  }
}
