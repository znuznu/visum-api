package znu.visum.components.externals.tmdb.infrastructure.adapters.mock;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.infrastructure.adapters.mock.MockExternalExceptions;
import znu.visum.components.externals.tmdb.infrastructure.adapters.mock.MockExternalResponses;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

/**
 * WIP - Adapter used in Route integration tests in order to avoid calling external APIs.
 * */
@Repository
@NoArgsConstructor
public class MockExternalConnector implements ExternalConnector {
  private MockExternalResponses responses;
  private MockExternalExceptions errors;

  public void setResponses(MockExternalResponses responses) {
    this.responses = responses;
  }

  public void setExceptions(MockExternalExceptions errors) {
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
        "No Responses or Errors set for MockExternalConnector. Method: searchMovies.");
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
