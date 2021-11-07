package znu.visum.components.history.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.history.domain.models.MovieViewingHistory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Represent a movie viewing history created.")
public class CreateMovieViewingHistoryRequest {
  @ApiModelProperty("A viewing date of the movie.")
  @NotNull
  private final LocalDate viewingDate;

  @ApiModelProperty("The movie identifier to which add a viewing date.")
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
    return new MovieViewingHistory(null, this.getViewingDate(), this.getMovieId());
  }

  public LocalDate getViewingDate() {
    return viewingDate;
  }

  public long getMovieId() {
    return movieId;
  }
}
