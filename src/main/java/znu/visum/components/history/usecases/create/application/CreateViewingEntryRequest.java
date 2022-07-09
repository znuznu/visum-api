package znu.visum.components.history.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import znu.visum.components.history.domain.ViewingEntry;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Schema(description = "Represents the request to add a viewing date to a movie.")
public class CreateViewingEntryRequest {

  @Schema(description = "A viewing date in ISO 8601 format (i.e. yyyy-MM-dd).")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull
  private final LocalDate viewingDate;

  @Schema(description = "The related movie identifier for which to add the viewing date.")
  @NotNull
  private final long movieId;

  @JsonCreator
  public CreateViewingEntryRequest(
      @JsonProperty("viewingDate")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
          LocalDate viewingDate,
      @JsonProperty("movieId") long movieId) {
    this.viewingDate = viewingDate;
    this.movieId = movieId;
  }

  public ViewingEntry toDomain() {
    return ViewingEntry.builder().date(this.getViewingDate()).movieId(this.getMovieId()).build();
  }
}
