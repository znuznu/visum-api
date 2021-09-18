package znu.visum.components.genres.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchGenreIdException extends NoSuchModelException {
  public NoSuchGenreIdException(String id) {
    super(id, DomainModel.GENRE);
  }
}
