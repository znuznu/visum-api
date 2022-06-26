package znu.visum.components.person.actors.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.person.domain.Person;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
public class Actor extends Person {
  private List<MovieFromActor> movies;
  private ActorMetadata metadata;

  public static Actor from(ActorFromMovie actor) {
    return Actor.builder()
        .id(actor.getId())
        .identity(actor.getIdentity())
        .movies(new ArrayList<>())
        .metadata(actor.getMetadata())
        .build();
  }
}
