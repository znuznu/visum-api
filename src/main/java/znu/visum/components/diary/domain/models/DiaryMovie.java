package znu.visum.components.diary.domain.models;

import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.ReviewFromMovie;
import znu.visum.core.errors.domain.VisumException;

import java.time.LocalDate;

public class DiaryMovie {
  private long id;
  private String title;
  private LocalDate releaseDate;
  private ReviewFromMovie review;
  private boolean isFavorite;
  private boolean isRewatch;
  private LocalDate viewingDate;
  private String posterUrl;

  public DiaryMovie() {}

  public DiaryMovie(
      long id,
      String title,
      LocalDate releaseDate,
      ReviewFromMovie review,
      boolean isFavorite,
      boolean isRewatch,
      LocalDate viewingDate,
      String posterUrl) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.review = review;
    this.isFavorite = isFavorite;
    this.isRewatch = isRewatch;
    this.viewingDate = viewingDate;
  }

  public static DiaryMovie from(Movie movie, long viewingHistoryId) {
    LocalDate viewingDate =
        movie.getViewingHistory().stream()
            .filter(history -> history.getId() == viewingHistoryId)
            .map(MovieViewingHistory::getViewingDate)
            .findFirst()
            .orElseThrow(
                () ->
                    new VisumException("Viewing history ID not found in the movie viewing dates."));

    boolean isRewatch =
        movie.getViewingHistory().stream()
            .anyMatch(history -> history.getViewingDate().isBefore(viewingDate));

    return new DiaryMovie.Builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .releaseDate(movie.getReleaseDate())
        .review(movie.getReview())
        .viewingDate(viewingDate)
        .favorite(movie.isFavorite())
        .rewatch(isRewatch)
        .posterUrl(movie.getMetadata() != null ? movie.getMetadata().getPosterUrl() : null)
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

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public ReviewFromMovie getReview() {
    return review;
  }

  public void setReview(ReviewFromMovie review) {
    this.review = review;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public boolean isRewatch() {
    return isRewatch;
  }

  public void setRewatch(boolean rewatch) {
    isRewatch = rewatch;
  }

  public LocalDate getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(LocalDate viewingDate) {
    this.viewingDate = viewingDate;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public void setPosterUrl(String posterUrl) {
    this.posterUrl = posterUrl;
  }

  public static final class Builder {
    private final DiaryMovie diaryMovie;

    public Builder() {
      this.diaryMovie = new DiaryMovie();
    }

    public static Builder aDiaryMovie() {
      return new Builder();
    }

    public Builder id(long id) {
      this.diaryMovie.id = id;
      return this;
    }

    public Builder title(String title) {
      this.diaryMovie.title = title;
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      this.diaryMovie.releaseDate = releaseDate;
      return this;
    }

    public Builder review(ReviewFromMovie review) {
      this.diaryMovie.review = review;
      return this;
    }

    public Builder viewingDate(LocalDate viewingDate) {
      this.diaryMovie.viewingDate = viewingDate;
      return this;
    }

    public Builder posterUrl(String posterUrl) {
      this.diaryMovie.posterUrl = posterUrl;
      return this;
    }

    public Builder favorite(boolean isFavorite) {
      this.diaryMovie.isFavorite = isFavorite;
      return this;
    }

    public Builder rewatch(boolean isRewatch) {
      this.diaryMovie.isRewatch = isRewatch;
      return this;
    }

    public DiaryMovie build() {
      return diaryMovie;
    }
  }
}
