package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.actors.domain.ActorMetadata;

@AllArgsConstructor
@Builder
@Getter
public class ActorFromMovie {

  @Setter private Long id;
  private String name;
  private String forename;
  private ActorMetadata metadata;

  public static ActorFromMovie from(Actor actor) {
    return ActorFromMovie.builder()
        .id(actor.getId())
        .metadata(actor.getMetadata())
        .name(actor.getName())
        .forename(actor.getForename())
        .build();
  }
}
