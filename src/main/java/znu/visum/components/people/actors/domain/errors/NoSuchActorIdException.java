package znu.visum.components.people.actors.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchActorIdException extends NoSuchModelException {
  public NoSuchActorIdException(String id) {
    super(id, DomainModel.ACTOR);
  }

  public static NoSuchActorIdException with(String id) {
    return new NoSuchActorIdException(id);
  }
}
