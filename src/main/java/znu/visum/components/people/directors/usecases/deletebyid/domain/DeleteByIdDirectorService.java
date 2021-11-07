package znu.visum.components.people.directors.usecases.deletebyid.domain;

import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;

public interface DeleteByIdDirectorService {
  void deleteById(long id) throws NoSuchDirectorIdException;
}
