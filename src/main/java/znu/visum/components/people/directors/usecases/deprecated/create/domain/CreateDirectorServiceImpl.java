package znu.visum.components.people.directors.usecases.deprecated.create.domain;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.DirectorRepository;

@Service
public class CreateDirectorServiceImpl implements CreateDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public CreateDirectorServiceImpl(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  @Override
  public Director save(Director director) {
    if (director.getId() != null) {
      throw new PersistentObjectException("Can't create Director with fixed id");
    }

    return directorRepository.save(director);
  }
}
