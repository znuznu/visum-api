package znu.visum.components.movies.usecases.getpage.domain;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

public interface GetPageMovieService {
  VisumPage<Movie> findPage(PageSearch<Movie> page);
}
