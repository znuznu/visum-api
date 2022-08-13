package znu.visum.components.genres.domain;

import znu.visum.core.exceptions.domain.Domain;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchGenreIdException extends NoSuchModelException {
  public NoSuchGenreIdException(String id) {
    super(id, Domain.GENRE);
  }
}
