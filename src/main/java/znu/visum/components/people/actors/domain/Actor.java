package znu.visum.components.people.actors.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.people.domain.People;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
public class Actor extends People {
  private List<MovieFromActor> movies;
  private ActorMetadata metadata;

  public static Actor from(ActorFromMovie actorFromMovie) {
    return Actor.builder()
        .id(actorFromMovie.getId())
        .name(actorFromMovie.getName())
        .forename(actorFromMovie.getForename())
        .movies(new ArrayList<>())
        .metadata(actorFromMovie.getMetadata())
        .build();
  }
}
