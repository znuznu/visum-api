package znu.visum.components.people.directors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;

@Service
public class GetByIdDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public GetByIdDirectorService(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public Director findById(long id) throws NoSuchDirectorIdException {
    return directorRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchDirectorIdException(Long.toString(id)));
  }
}
