package znu.visum.components.externals.tmdb.infrastructure.adapters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryExceptions;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryResponses;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

/** Adapter used in Route integration tests in order to avoid calling external APIs. */
@Repository
@NoArgsConstructor
public class ExternalInMemoryConnector implements ExternalConnector {
  private TmdbInMemoryResponses responses;
  private TmdbInMemoryExceptions errors;

  public void setResponses(TmdbInMemoryResponses responses) {
    this.responses = responses;
  }

  public void setExceptions(TmdbInMemoryExceptions errors) {
    this.errors = errors;
  }

  @Override
  public VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber) {
    if (this.errors != null && this.errors.getSearchMoviesException() != null) {
      throw this.errors.getSearchMoviesException();
    }

    if (this.responses != null && this.responses.getSearchMoviesResponse() != null) {
      return this.responses.getSearchMoviesResponse();
    }

    throw new UnsupportedOperationException(
        "No Responses or Errors set for TmdbInMemoryConnector. Method: searchMovies.");
  }

  @Override
  public VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber, String region) {
    throw new UnsupportedOperationException();
  }

  @Override
  public VisumPage<ExternalNowPlayingMovie> getNowPlayingMovies(int pageNumber, String region) {
    return null;
  }

  @Override
  public Optional<ExternalMovie> getMovieById(long movieId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getConfigurationRootPosterUrl() {
    throw new UnsupportedOperationException();
  }
}
