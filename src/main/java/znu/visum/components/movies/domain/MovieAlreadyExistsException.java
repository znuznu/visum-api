package znu.visum.components.movies.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.ResourceAlreadyExistsException;

public class MovieAlreadyExistsException extends ResourceAlreadyExistsException {

  public MovieAlreadyExistsException() {
    super(Domain.MOVIE);
  }
}
