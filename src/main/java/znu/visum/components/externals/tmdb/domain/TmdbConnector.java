package znu.visum.components.externals.tmdb.domain;

import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieCredits;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.components.externals.domain.ExternalUpcomingMovie;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface TmdbConnector {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);

  VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber);

  Optional<ExternalMovie> getMovieById(long movieId);

  Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId, String basePosterUrl);

  String getConfigurationBasePosterUrl();
}
