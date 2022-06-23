package znu.visum.components.people.directors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.DirectorRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public GetPageDirectorService(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public VisumPage<Director> findPage(int limit, int offset, Sort sort, String search) {
    return directorRepository.findPage(limit, offset, sort, search);
  }
}
