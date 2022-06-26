package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorMetadata;
import znu.visum.components.person.domain.Identity;

@AllArgsConstructor
@Builder
@Getter
public class ActorFromMovie {

  @Setter private Long id;
  private Identity identity;
  private ActorMetadata metadata;

  public static ActorFromMovie from(Actor actor) {
    return ActorFromMovie.builder()
        .id(actor.getId())
        .metadata(actor.getMetadata())
        .identity(actor.getIdentity())
        .build();
  }
}
