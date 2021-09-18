package znu.visum.components.people.directors.usecases.getbyid.domain;

import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;
import znu.visum.components.people.directors.domain.models.Director;

public interface GetByIdDirectorService {
  Director findById(long id) throws NoSuchDirectorIdException;
}
