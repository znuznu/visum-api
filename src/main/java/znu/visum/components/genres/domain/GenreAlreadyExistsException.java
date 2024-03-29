package znu.visum.components.genres.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.ResourceAlreadyExistsException;

public class GenreAlreadyExistsException extends ResourceAlreadyExistsException {

  public GenreAlreadyExistsException() {
    super(Domain.GENRE);
  }
}
