package znu.visum.components.externals.tmdb.infrastructure.adapters.models;

import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

public class TmdbInMemoryResponses {
  VisumPage<ExternalMovieFromSearch> searchMovies;

  public TmdbInMemoryResponses() {}

  public VisumPage<ExternalMovieFromSearch> getSearchMoviesResponse() {
    return searchMovies;
  }

  public static final class Builder {
    private final TmdbInMemoryResponses responses;

    public Builder() {
      this.responses = new TmdbInMemoryResponses();
    }

    public Builder searchMovies(VisumPage<ExternalMovieFromSearch> searchMovies) {
      this.responses.searchMovies = searchMovies;
      return this;
    }

    public TmdbInMemoryResponses build() {
      return this.responses;
    }
  }
}
