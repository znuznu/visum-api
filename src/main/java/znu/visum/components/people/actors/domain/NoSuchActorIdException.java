package znu.visum.components.people.actors.domain;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchActorIdException extends NoSuchModelException {

  private NoSuchActorIdException(String id) {
    super(id, DomainModel.ACTOR);
  }

  public static NoSuchActorIdException withId(String id) {
    return new NoSuchActorIdException(id);
  }
}
