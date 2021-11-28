package znu.visum.components.people.directors.usecases.getpage.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetPageDirectorService {
  VisumPage<Director> findPage(int limit, int offset, Sort sort, String search);
}
