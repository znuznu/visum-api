package znu.visum.components.externals.tmdb.domain;

import znu.visum.components.externals.domain.*;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface TmdbConnector {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);

  VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber, String region);

  VisumPage<ExternalNowPlayingMovie> getNowPlayingMovies(int pageNumber, String region);

  Optional<ExternalMovie> getMovieById(long movieId);

  Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId);

  String getConfigurationRootPosterUrl();
}
