package znu.visum.components.genres.usecases.getpage.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.genres.domain.models.Genre;

import javax.validation.constraints.Min;

@ApiModel("Represents a Page of Genres.")
public class GenreFromPageResponse {
  @ApiModelProperty("The identifier of the Genre.")
  @Min(1)
  private final long id;

  @ApiModelProperty("The type of the Genre.")
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
