package znu.visum.components.person.actors.usecases.getpage.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.usecases.getpage.domain.PageActor;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a page of actors.")
public class PageActorResponse {

  @Schema(description = "The actor identifier.")
  private final long id;

  @Schema(description = "The actor name.")
  private final String name;

  @Schema(description = "The actor forename.")
  private final String forename;

  public static PageActorResponse from(PageActor actor) {
    return new PageActorResponse(
        actor.getId(), actor.getIdentity().getName(), actor.getIdentity().getForename());
  }
}
