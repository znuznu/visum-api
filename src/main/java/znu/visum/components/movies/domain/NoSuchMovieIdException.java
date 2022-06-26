package znu.visum.components.movies.domain;

import znu.visum.core.exceptions.domain.DomainModel;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchMovieIdException extends NoSuchModelException {
  public NoSuchMovieIdException(String id) {
    super(id, DomainModel.MOVIE);
  }

  public static NoSuchMovieIdException with(String id) {
    return new NoSuchMovieIdException(id);
  }
}
