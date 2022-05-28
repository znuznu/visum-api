package znu.visum.components.genres.usecases.create.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Schema(description = "Represent a genre created.")
public class CreateGenreResponse {
  @Schema(description = "The identifier of the Genre created.", example = "1", required = true)
  @Min(1)
  private final Long id;

  @Schema(description = "The type of the Genre created.", example = "Horror", required = true)
  @NotEmpty(message = "Type cannot be empty.")
  private final String type;

  public static CreateGenreResponse from(Genre genre) {
    return new CreateGenreResponse(genre.getId(), genre.getType());
  }
}
