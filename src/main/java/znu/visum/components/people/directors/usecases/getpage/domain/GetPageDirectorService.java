package znu.visum.components.people.directors.usecases.getpage.domain;

import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

public interface GetPageDirectorService {
  VisumPage<Director> findPage(PageSearch<Director> page);
}
