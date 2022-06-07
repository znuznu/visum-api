package znu.visum.components.movies.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.ResourceAlreadyExistsException;

public class MovieAlreadyExistsException extends ResourceAlreadyExistsException {

  public MovieAlreadyExistsException() {
    super(DomainModel.MOVIE);
  }
}
