package znu.visum.components.movies.domain;

import znu.visum.core.exceptions.domain.DomainModel;
import znu.visum.core.exceptions.domain.ResourceAlreadyExistsException;

public class MovieAlreadyExistsException extends ResourceAlreadyExistsException {

  public MovieAlreadyExistsException() {
    super(DomainModel.MOVIE);
  }
}
