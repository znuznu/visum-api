package znu.visum.components.externals.tmdb.usecases.searchmovies.domain;

import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

public interface SearchTmdbMoviesService {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);
}
