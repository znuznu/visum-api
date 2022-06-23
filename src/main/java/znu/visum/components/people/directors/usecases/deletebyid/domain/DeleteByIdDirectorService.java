package znu.visum.components.people.directors.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;

@Service
public class DeleteByIdDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public DeleteByIdDirectorService(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public void deleteById(long id) throws NoSuchDirectorIdException {
    if (directorRepository.findById(id).isEmpty()) {
      throw new NoSuchDirectorIdException(Long.toString(id));
    }

    directorRepository.deleteById(id);
  }
}
