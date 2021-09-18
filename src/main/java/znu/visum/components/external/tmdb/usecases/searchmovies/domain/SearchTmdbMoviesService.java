package znu.visum.components.external.tmdb.usecases.searchmovies.domain;

import znu.visum.components.external.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

public interface SearchTmdbMoviesService {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);
}
