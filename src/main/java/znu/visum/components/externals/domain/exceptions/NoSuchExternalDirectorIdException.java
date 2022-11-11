package znu.visum.components.externals.domain.exceptions;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchExternalDirectorIdException extends NoSuchModelException {

  private NoSuchExternalDirectorIdException(long id) {
    super(String.valueOf(id), Domain.EXTERNAL_DIRECTOR);
  }

  public static NoSuchExternalDirectorIdException withId(long id) {
    return new NoSuchExternalDirectorIdException(id);
  }
}
