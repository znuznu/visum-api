package znu.visum.components.movies.domain.models;

import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDateTime;

public class ReviewFromMovie {
  private Long id;

  private String content;

  private LocalDateTime updateDate;

  private LocalDateTime creationDate;

  private int grade;

  private Long movieId;

  public ReviewFromMovie() {}

  public ReviewFromMovie(
      Long id,
      String content,
      LocalDateTime updateDate,
      LocalDateTime creationDate,
      int grade,
      Long movieId) {
    this.id = id;
    this.content = content;
    this.updateDate = updateDate;
    this.creationDate = creationDate;
    this.grade = grade;
    this.movieId = movieId;
  }

  public static ReviewFromMovie from(Review review) {
    return new ReviewFromMovie(
        review.getId(),
        review.getContent(),
        review.getUpdateDate(),
        review.getCreationDate(),
        review.getGrade(),
        review.getMovie().getId());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public Long getMovieId() {
    return movieId;
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  public static final class Builder {
    private final ReviewFromMovie reviewFromMovie;

    public Builder() {
      reviewFromMovie = new ReviewFromMovie();
    }

    public Builder id(Long id) {
      reviewFromMovie.setId(id);
      return this;
    }

    public Builder content(String content) {
      reviewFromMovie.setContent(content);
      return this;
    }

    public Builder updateDate(LocalDateTime updateDate) {
      reviewFromMovie.setUpdateDate(updateDate);
      return this;
    }

    public Builder creationDate(LocalDateTime creationDate) {
      reviewFromMovie.setCreationDate(creationDate);
      return this;
    }

    public Builder grade(int grade) {
      reviewFromMovie.setGrade(grade);
      return this;
    }

    public Builder movieId(Long movieId) {
      reviewFromMovie.setMovieId(movieId);
      return this;
    }

    public ReviewFromMovie build() {
      return this.reviewFromMovie;
    }
  }
}
