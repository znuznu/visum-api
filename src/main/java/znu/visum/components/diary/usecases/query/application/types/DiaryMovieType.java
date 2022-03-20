package znu.visum.components.diary.usecases.query.application.types;

import znu.visum.components.diary.domain.models.DiaryMovie;

import java.time.LocalDate;

public class DiaryMovieType {
  private long id;

  private String title;

  private String posterUrl;

  private LocalDate releaseDate;

  private Integer grade;

  private boolean isFavorite;

  private boolean isRewatch;

  private Long reviewId;

  public DiaryMovieType() {}

  public DiaryMovieType(
      long id,
      String title,
      String posterUrl,
      LocalDate releaseDate,
      Integer grade,
      boolean isFavorite,
      boolean isRewatch,
      Long reviewId) {
    this.title = title;
    this.posterUrl = posterUrl;
    this.releaseDate = releaseDate;
    this.grade = grade;
    this.isFavorite = isFavorite;
    this.isRewatch = isRewatch;
    this.reviewId = reviewId;
  }

  public static DiaryMovieType from(DiaryMovie movie) {
    return new Builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .posterUrl(movie.getPosterUrl())
        .releaseDate(movie.getReleaseDate())
        .grade(movie.getReview() != null ? movie.getReview().getGrade() : null)
        .reviewId(movie.getReview() != null ? movie.getReview().getId() : null)
        .isFavorite(movie.isFavorite())
        .isRewatch(movie.isRewatch())
        .build();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public void setPosterUrl(String posterUrl) {
    this.posterUrl = posterUrl;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public boolean getIsFavorite() {
    return isFavorite;
  }

  public void setIsFavorite(boolean isFavorite) {
    this.isFavorite = isFavorite;
  }

  public boolean getIsRewatch() {
    return isRewatch;
  }

  public void setIsRewatch(boolean isRewatch) {
    this.isRewatch = isRewatch;
  }

  public Long getReviewId() {
    return reviewId;
  }

  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryMovieType that = (DiaryMovieType) o;
    return java.util.Objects.equals(id, that.id)
        && java.util.Objects.equals(title, that.title)
        && java.util.Objects.equals(posterUrl, that.posterUrl)
        && java.util.Objects.equals(releaseDate, that.releaseDate)
        && java.util.Objects.equals(grade, that.grade)
        && isFavorite == that.isFavorite
        && isRewatch == that.isRewatch
        && java.util.Objects.equals(reviewId, that.reviewId);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(
        id, title, posterUrl, releaseDate, grade, isFavorite, isRewatch, reviewId);
  }

  public static class Builder {
    private long id;

    private String title;

    private String posterUrl;

    private LocalDate releaseDate;

    private Integer grade;

    private boolean isFavorite;

    private boolean isRewatch;

    private Long reviewId;

    public DiaryMovieType build() {
      DiaryMovieType result = new DiaryMovieType();
      result.id = this.id;
      result.title = this.title;
      result.posterUrl = this.posterUrl;
      result.releaseDate = this.releaseDate;
      result.grade = this.grade;
      result.isFavorite = this.isFavorite;
      result.isRewatch = this.isRewatch;
      result.reviewId = this.reviewId;
      return result;
    }

    public DiaryMovieType.Builder id(long id) {
      this.id = id;
      return this;
    }

    public DiaryMovieType.Builder title(String title) {
      this.title = title;
      return this;
    }

    public DiaryMovieType.Builder posterUrl(String posterUrl) {
      this.posterUrl = posterUrl;
      return this;
    }

    public DiaryMovieType.Builder releaseDate(LocalDate releaseDate) {
      this.releaseDate = releaseDate;
      return this;
    }

    public DiaryMovieType.Builder grade(Integer grade) {
      this.grade = grade;
      return this;
    }

    public DiaryMovieType.Builder isFavorite(boolean isFavorite) {
      this.isFavorite = isFavorite;
      return this;
    }

    public DiaryMovieType.Builder isRewatch(boolean isRewatch) {
      this.isRewatch = isRewatch;
      return this;
    }

    public DiaryMovieType.Builder reviewId(Long reviewId) {
      this.reviewId = reviewId;
      return this;
    }
  }
}
