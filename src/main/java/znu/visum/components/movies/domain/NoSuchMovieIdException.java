package znu.visum.components.movies.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchMovieIdException extends NoSuchModelException {
  public NoSuchMovieIdException(String id) {
    super(id, Domain.MOVIE);
  }

  public static NoSuchMovieIdException with(String id) {
    return new NoSuchMovieIdException(id);
  }
}
