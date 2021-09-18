package znu.visum.components.history.usecases.deprecated.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import znu.visum.components.history.domain.models.MovieViewingHistory;

import java.time.LocalDate;

public class GetByIdMovieViewingHistoryResponse {
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate viewingDate;

  private final long id;

  private final long movieId;

  public GetByIdMovieViewingHistoryResponse(LocalDate viewingDate, long id, long movieId) {
    this.viewingDate = viewingDate;
    this.id = id;
    this.movieId = movieId;
  }

  public static GetByIdMovieViewingHistoryResponse from(MovieViewingHistory movieViewingHistory) {
    return new GetByIdMovieViewingHistoryResponse(
        movieViewingHistory.getViewingDate(),
        movieViewingHistory.getId(),
        movieViewingHistory.getMovieId());
  }

  public LocalDate getViewingDate() {
    return viewingDate;
  }

  public long getId() {
    return id;
  }

  public long getMovieId() {
    return movieId;
  }
}
