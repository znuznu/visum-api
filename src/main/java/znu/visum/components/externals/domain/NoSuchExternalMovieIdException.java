package znu.visum.components.externals.domain;

import znu.visum.core.exceptions.domain.DomainModel;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchExternalMovieIdException extends NoSuchModelException {

  private NoSuchExternalMovieIdException(long id) {
    super(String.valueOf(id), DomainModel.EXTERNAL_MOVIE);
  }

  public static NoSuchExternalMovieIdException withId(long id) {
    return new NoSuchExternalMovieIdException(id);
  }
}
