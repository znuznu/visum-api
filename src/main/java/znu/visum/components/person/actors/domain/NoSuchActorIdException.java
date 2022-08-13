package znu.visum.components.person.actors.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchActorIdException extends NoSuchModelException {

  private NoSuchActorIdException(String id) {
    super(id, Domain.ACTOR);
  }

  public static NoSuchActorIdException withId(String id) {
    return new NoSuchActorIdException(id);
  }
}
