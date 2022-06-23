package znu.visum.components.history.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.history.domain.MovieViewingHistory;

import java.time.LocalDate;

@Schema(description = "Represents a movie to create.")
public class CreateMovieViewingHistoryResponse {
  @Schema(description = "The movie viewing date created.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate viewingDate;

  @Schema(description = "The identifier of the movie viewing history created.")
  private final long id;

  @Schema(description = "The movie identifier of the movie viewing date created.")
  private final long movieId;

  public CreateMovieViewingHistoryResponse(LocalDate viewingDate, long id, long movieId) {
    this.id = id;
    this.viewingDate = viewingDate;
    this.movieId = movieId;
  }

  public static CreateMovieViewingHistoryResponse from(MovieViewingHistory movieViewingHistory) {
    return new CreateMovieViewingHistoryResponse(
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
