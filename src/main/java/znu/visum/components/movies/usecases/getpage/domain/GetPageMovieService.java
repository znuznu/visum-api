package znu.visum.components.movies.usecases.getpage.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetPageMovieService {
  // TODO Spring Sort is not supposed to be here!
  VisumPage<Movie> findPage(int limit, int offset, Sort sort, String search);
}
