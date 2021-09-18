package znu.visum.components.people.directors.usecases.deprecated.update.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;

import java.util.NoSuchElementException;

@Service
public class UpdateDirectorServiceImpl implements UpdateDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public UpdateDirectorServiceImpl(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  @Override
  public Director update(Director director) {
    directorRepository
        .findById(director.getId())
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format("No Director with id %d", director.getId())));

    return directorRepository.save(director);
  }
}
