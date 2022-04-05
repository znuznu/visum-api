package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;

@Schema(description = "Represent a Genre.")
public class GetByIdGenreResponse {
  @Schema(description = "The identifier of the Genre.")
  @Min(1)
  private final Long id;

  @Schema(description = "The type of the Genre.")
  private final String type;

  public GetByIdGenreResponse(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static GetByIdGenreResponse from(Genre genre) {
    return new GetByIdGenreResponse(genre.getId(), genre.getType());
  }

  public Long getId() {
    return id;
  }

  public String getType() {
    return type;
  }
}
