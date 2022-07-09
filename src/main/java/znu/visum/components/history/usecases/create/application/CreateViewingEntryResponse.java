package znu.visum.components.history.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.history.domain.ViewingEntry;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a viewing entry created.")
public class CreateViewingEntryResponse {

  @Schema(description = "A viewing date in ISO 8601 format (i.e. yyyy-MM-dd).")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private final LocalDate date;

  @Schema(description = "The identifier of the created entry.")
  private final long id;

  @Schema(description = "The identifier of the related movie.")
  private final long movieId;

  public static CreateViewingEntryResponse from(ViewingEntry viewingEntry) {
    return CreateViewingEntryResponse.builder()
        .date(viewingEntry.getDate())
        .id(viewingEntry.getId())
        .movieId(viewingEntry.getMovieId())
        .build();
  }
}
