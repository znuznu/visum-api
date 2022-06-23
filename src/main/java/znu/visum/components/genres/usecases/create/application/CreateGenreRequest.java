package znu.visum.components.genres.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.genres.domain.Genre;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Request body used to create a genre.")
public class CreateGenreRequest {
  @Schema(
      description = "A type of genre, must not already exist.",
      example = "Horror",
      required = true)
  @NotNull
  @NotEmpty(message = "Type cannot be empty.")
  private final String type;

  @JsonCreator
  public CreateGenreRequest(@JsonProperty("type") String type) {
    this.type = type;
  }

  public Genre toDomain() {
    return new Genre(null, this.getType());
  }

  public String getType() {
    return type;
  }
}
