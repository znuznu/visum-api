package znu.visum.components.genres.domain;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.ResourceAlreadyExistsException;

public class GenreAlreadyExistsException extends ResourceAlreadyExistsException {

  public GenreAlreadyExistsException() {
    super(DomainModel.GENRE);
  }
}
