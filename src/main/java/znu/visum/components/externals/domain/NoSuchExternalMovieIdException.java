package znu.visum.components.externals.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchExternalMovieIdException extends NoSuchModelException {

  private NoSuchExternalMovieIdException(long id) {
    super(String.valueOf(id), Domain.EXTERNAL_MOVIE);
  }

  public static NoSuchExternalMovieIdException withId(long id) {
    return new NoSuchExternalMovieIdException(id);
  }
}
