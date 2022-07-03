package znu.visum.components.person.directors.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.directors.domain.NoSuchDirectorIdException;

@Service
public class DeleteDirectorById {

  private final DirectorRepository directorRepository;

  @Autowired
  public DeleteDirectorById(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public void process(long id) {
    if (!directorRepository.existsById(id)) {
      throw new NoSuchDirectorIdException(Long.toString(id));
    }

    directorRepository.deleteById(id);
  }
}
