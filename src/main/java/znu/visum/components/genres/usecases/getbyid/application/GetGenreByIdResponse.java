package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.genres.domain.Genre;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
@Schema(description = "Represent a Genre.")
public class GetGenreByIdResponse {
  @Schema(description = "The identifier of the Genre.")
  @Min(1)
  private final Long id;

  @Schema(description = "The type of the Genre.")
  private final String type;

  public static GetGenreByIdResponse from(Genre genre) {
    return new GetGenreByIdResponse(genre.getId(), genre.getType());
  }
}
