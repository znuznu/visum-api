package znu.visum.components.person.directors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageDirector {
  private final DirectorRepository directorRepository;

  @Autowired
  public GetPageDirector(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public VisumPage<Director> process(int limit, int offset, Sort sort, String search) {
    return directorRepository.findPage(limit, offset, sort, search);
  }
}
