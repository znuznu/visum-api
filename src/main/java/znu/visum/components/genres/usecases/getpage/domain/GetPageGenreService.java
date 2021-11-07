package znu.visum.components.genres.usecases.getpage.domain;

import znu.visum.components.genres.domain.models.Genre;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

public interface GetPageGenreService {
  VisumPage<Genre> findPage(PageSearch<Genre> page);
}
