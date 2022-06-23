package znu.visum.components.genres.usecases.getpage.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.genres.domain.Genre;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a Page of Genres.")
public class GenreFromPageResponse {
  @Schema(description = "The identifier of the Genre.")
  @Min(1)
  private final long id;

  @Schema(description = "The type of the Genre.")
  private final String type;

  public static GenreFromPageResponse from(Genre genre) {
    return new GenreFromPageResponse(genre.getId(), genre.getType());
  }
}
