package znu.visum.components.people.actors.usecases.getpage.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.people.actors.domain.models.Actor;

@ApiModel("Represents a page of actors.")
public class ActorFromPageResponse {
  @ApiModelProperty("The actor identifier.")
  private final long id;

  @ApiModelProperty("The actor name.")
  private final String name;

  @ApiModelProperty("The actor forename.")
  private final String forename;

  public ActorFromPageResponse(long id, String name, String forename) {
    this.id = id;
    this.name = name;
    this.forename = forename;
  }

  public static ActorFromPageResponse from(Actor actor) {
    return new ActorFromPageResponse(actor.getId(), actor.getName(), actor.getForename());
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
