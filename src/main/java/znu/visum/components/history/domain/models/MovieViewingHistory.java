package znu.visum.components.history.domain.models;

import java.time.LocalDate;

public class MovieViewingHistory {
  private Long id;

  private LocalDate viewingDate;

  private long movieId;

  public MovieViewingHistory() {}

  public MovieViewingHistory(Long id, LocalDate viewingDate, long movieId) {
    this.id = id;
    this.viewingDate = viewingDate;
    this.movieId = movieId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(LocalDate viewingDate) {
    this.viewingDate = viewingDate;
  }

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  public static final class Builder {
    private final MovieViewingHistory movieViewingHistory;

    public Builder() {
      movieViewingHistory = new MovieViewingHistory();
    }

    public Builder id(Long id) {
      movieViewingHistory.setId(id);
      return this;
    }

    public Builder viewingDate(LocalDate viewingDate) {
      movieViewingHistory.setViewingDate(viewingDate);
      return this;
    }

    public Builder movieId(long movieId) {
      movieViewingHistory.setMovieId(movieId);
      return this;
    }

    public MovieViewingHistory build() {
      return movieViewingHistory;
    }
  }
}
