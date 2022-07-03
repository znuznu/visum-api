package znu.visum.components.person.actors.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import znu.visum.components.person.domain.Person;

import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
public class Actor extends Person {

  private List<MovieFromActor> movies;
  private ActorMetadata metadata;
}
