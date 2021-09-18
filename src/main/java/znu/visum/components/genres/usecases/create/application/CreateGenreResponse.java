package znu.visum.components.genres.usecases.create.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Represent a genre created.")
public class CreateGenreResponse {
  @ApiModelProperty(value = "The identifier of the Genre created.", example = "1", required = true)
  @Min(1)
  private final Long id;

  @ApiModelProperty(value = "The type of the Genre created.", example = "Horror", required = true)
  @NotEmpty(message = "Type cannot be empty.")
  private final String type;

  public CreateGenreResponse(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static CreateGenreResponse from(Genre genre) {
    return new CreateGenreResponse(genre.getId(), genre.getType());
  }

  public Long getId() {
    return id;
  }

  public String getType() {
    return type;
  }
}
