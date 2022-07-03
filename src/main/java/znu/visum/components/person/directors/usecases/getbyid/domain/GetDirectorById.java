package znu.visum.components.person.directors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.directors.domain.NoSuchDirectorIdException;

@Service
public class GetDirectorById {

  private final DirectorRepository directorRepository;

  @Autowired
  public GetDirectorById(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public Director process(long id) {
    return directorRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchDirectorIdException(Long.toString(id)));
  }
}
