package znu.visum.components.genres.usecases.getpage.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetPageGenreService {
  VisumPage<Genre> findPage(int limit, int offset, Sort sort, String search);
}
