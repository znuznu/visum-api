package znu.visum.components.people.directors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.DirectorRepository;
import znu.visum.components.people.directors.domain.NoSuchDirectorIdException;

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
