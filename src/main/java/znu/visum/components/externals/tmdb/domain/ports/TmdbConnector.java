package znu.visum.components.externals.tmdb.domain.ports;

import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieCredits;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface TmdbConnector {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);

  Optional<ExternalMovie> getMovieById(long movieId);

  Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId);

  String getConfigurationBasePosterUrl();
}
