package znu.visum.components.externals.domain;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchExternalMovieIdException extends NoSuchModelException {

  private NoSuchExternalMovieIdException(long id) {
    super(String.valueOf(id), DomainModel.EXTERNAL_MOVIE);
  }

  public static NoSuchExternalMovieIdException withId(long id) {
    return new NoSuchExternalMovieIdException(id);
  }
}
