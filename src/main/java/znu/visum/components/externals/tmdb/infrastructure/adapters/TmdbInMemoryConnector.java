package znu.visum.components.externals.tmdb.infrastructure.adapters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieCredits;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.components.externals.domain.models.ExternalUpcomingMovie;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryExceptions;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryResponses;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

/** Adapter used in Route integration tests in order to avoid calling external APIs. */
@Repository
@NoArgsConstructor
public class TmdbInMemoryConnector implements TmdbConnector {
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
  public VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber) {
    throw new UnsupportedOperationException();
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
  public String getConfigurationBasePosterUrl() {
    throw new UnsupportedOperationException();
  }
}
