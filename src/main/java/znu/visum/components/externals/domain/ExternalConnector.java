package znu.visum.components.externals.domain;

import znu.visum.components.externals.domain.models.*;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.List;
import java.util.Optional;

public interface ExternalConnector {
  VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber);

  VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber, String region);

  VisumPage<ExternalNowPlayingMovie> getNowPlayingMovies(int pageNumber, String region);

  Optional<ExternalMovie> getMovieById(long movieId);

  Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId);

  /** Returns an empty if the director does not exist */
  Optional<List<ExternalDirectorMovie>> getMoviesByDirectorId(long directorId);
}
