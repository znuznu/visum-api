package znu.visum.components.movies.domain;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchMovieIdException extends NoSuchModelException {
  public NoSuchMovieIdException(String id) {
    super(id, DomainModel.MOVIE);
  }

  public static NoSuchMovieIdException with(String id) {
    return new NoSuchMovieIdException(id);
  }
}
