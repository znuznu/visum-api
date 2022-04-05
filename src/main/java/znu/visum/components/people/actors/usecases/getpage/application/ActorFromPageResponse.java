package znu.visum.components.people.actors.usecases.getpage.application;

import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.people.actors.domain.models.Actor;

@Schema(description = "Represents a page of actors.")
public class ActorFromPageResponse {
  @Schema(description = "The actor identifier.")
  private final long id;

  @Schema(description = "The actor name.")
  private final String name;

  @Schema(description = "The actor forename.")
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
