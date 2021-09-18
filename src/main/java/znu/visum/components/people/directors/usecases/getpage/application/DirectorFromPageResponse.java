package znu.visum.components.people.directors.usecases.getpage.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.people.directors.domain.models.Director;

@ApiModel("Represents a page of directors.")
public class DirectorFromPageResponse {
  @ApiModelProperty("The director identifier.")
  private final long id;

  @ApiModelProperty("The director name.")
  private final String name;

  @ApiModelProperty("The director forename.")
  private final String forename;

  public DirectorFromPageResponse(long id, String name, String forename) {
    this.id = id;
    this.name = name;
    this.forename = forename;
  }

  public static DirectorFromPageResponse from(Director director) {
    return new DirectorFromPageResponse(
        director.getId(), director.getName(), director.getForename());
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getForename() {
    return forename;
  }
}
