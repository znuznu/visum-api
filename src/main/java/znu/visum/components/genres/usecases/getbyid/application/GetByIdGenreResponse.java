package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
@Schema(description = "Represent a Genre.")
public class GetByIdGenreResponse {
  @Schema(description = "The identifier of the Genre.")
  @Min(1)
  private final Long id;

  @Schema(description = "The type of the Genre.")
  private final String type;

  public static GetByIdGenreResponse from(Genre genre) {
    return new GetByIdGenreResponse(genre.getId(), genre.getType());
  }
}
