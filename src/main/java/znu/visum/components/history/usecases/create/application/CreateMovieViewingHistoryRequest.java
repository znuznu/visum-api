package znu.visum.components.history.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import znu.visum.components.history.domain.MovieViewingHistory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Schema(description = "Represent a movie viewing history created.")
public class CreateMovieViewingHistoryRequest {
  @Schema(description = "A viewing date of the movie.")
  @NotNull
  private final LocalDate viewingDate;

  @Schema(description = "The movie identifier to which add a viewing date.")
  @NotNull
  private final long movieId;

  @JsonCreator
  public CreateMovieViewingHistoryRequest(
      @JsonProperty("viewingDate")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
          LocalDate viewingDate,
      @JsonProperty("movieId") long movieId) {
    this.viewingDate = viewingDate;
    this.movieId = movieId;
  }

  public MovieViewingHistory toDomain() {
    return MovieViewingHistory.builder()
        .viewingDate(this.getViewingDate())
        .movieId(this.getMovieId())
        .build();
  }
}
