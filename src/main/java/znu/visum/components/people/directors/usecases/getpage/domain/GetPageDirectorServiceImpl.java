package znu.visum.components.people.directors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Service
public class GetPageDirectorServiceImpl implements GetPageDirectorService {
  private final DirectorRepository directorRepository;

  @Autowired
  public GetPageDirectorServiceImpl(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  @Override
  public VisumPage<Director> findPage(PageSearch<Director> pageSearch) {
    return directorRepository.findPage(pageSearch);
  }
}
