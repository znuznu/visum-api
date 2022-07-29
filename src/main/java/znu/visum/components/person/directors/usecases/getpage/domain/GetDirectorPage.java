package znu.visum.components.person.directors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetDirectorPage {
  private final DirectorRepository directorRepository;

  @Autowired
  public GetDirectorPage(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  public VisumPage<PageDirector> process(int limit, int offset, Sort sort, String search) {
    return directorRepository.findPage(limit, offset, sort, search);
  }
}
