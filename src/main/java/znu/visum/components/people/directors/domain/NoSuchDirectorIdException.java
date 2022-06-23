package znu.visum.components.people.directors.domain;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchDirectorIdException extends NoSuchModelException {
  public NoSuchDirectorIdException(String id) {
    super(id, DomainModel.DIRECTOR);
  }

  public static NoSuchDirectorIdException with(String id) {
    return new NoSuchDirectorIdException(id);
  }
}
