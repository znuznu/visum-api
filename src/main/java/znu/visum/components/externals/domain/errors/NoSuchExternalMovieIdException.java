package znu.visum.components.externals.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchExternalMovieIdException extends NoSuchModelException {

  public NoSuchExternalMovieIdException(String id) {
    super(id, DomainModel.EXTERNAL_MOVIE);
  }
}
