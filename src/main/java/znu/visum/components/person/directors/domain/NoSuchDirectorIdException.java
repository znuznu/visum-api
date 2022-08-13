package znu.visum.components.person.directors.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchDirectorIdException extends NoSuchModelException {
  public NoSuchDirectorIdException(String id) {
    super(id, Domain.DIRECTOR);
  }

  public static NoSuchDirectorIdException with(String id) {
    return new NoSuchDirectorIdException(id);
  }
}
