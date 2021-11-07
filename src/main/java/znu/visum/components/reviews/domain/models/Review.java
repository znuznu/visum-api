package znu.visum.components.reviews.domain.models;

import java.time.LocalDateTime;

public class Review {
  private Long id;

  private String content;

  private LocalDateTime updateDate;

  private LocalDateTime creationDate;

  private int grade;

  private MovieFromReview movieFromReview;

  public Review(
      Long id,
      String content,
      LocalDateTime updateDate,
      LocalDateTime creationDate,
      int grade,
      MovieFromReview movieFromReview) {
    this.id = id;
    this.content = content;
    this.updateDate = updateDate;
    this.creationDate = creationDate;
    this.grade = grade;
    this.movieFromReview = movieFromReview;
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

  public MovieFromReview getMovie() {
    return movieFromReview;
  }

  public void setMovie(MovieFromReview movieFromReview) {
    this.movieFromReview = movieFromReview;
  }
}
