package znu.visum.components.externals.tmdb.usecases.getupcoming.domain;

import znu.visum.components.externals.domain.models.ExternalUpcomingMovie;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetUpcomingTmdbMoviesService {
  VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber);
}
