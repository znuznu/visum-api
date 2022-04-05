package znu.visum.components.genres.usecases.getpage.application;

import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;

@Schema(description = "Represents a Page of Genres.")
public class GenreFromPageResponse {
  @Schema(description = "The identifier of the Genre.")
  @Min(1)
  private final long id;

  @Schema(description = "The type of the Genre.")
  private final String type;

  public GenreFromPageResponse(long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static GenreFromPageResponse from(Genre genre) {
    return new GenreFromPageResponse(genre.getId(), genre.getType());
  }

  public long getId() {
    return id;
  }

  public String getType() {
    return type;
  }
}
