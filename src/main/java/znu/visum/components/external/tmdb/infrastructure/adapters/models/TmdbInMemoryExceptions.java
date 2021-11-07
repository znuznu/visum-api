package znu.visum.components.external.tmdb.infrastructure.adapters.models;

public class TmdbInMemoryExceptions {
  private RuntimeException searchMovies;

  public TmdbInMemoryExceptions() {}

  public RuntimeException getSearchMoviesException() {
    return searchMovies;
  }

  public static final class Builder {
    private final TmdbInMemoryExceptions exceptions;

    public Builder() {
      this.exceptions = new TmdbInMemoryExceptions();
    }

    public Builder searchMovies(RuntimeException searchMovies) {
      this.exceptions.searchMovies = searchMovies;
      return this;
    }

    public TmdbInMemoryExceptions build() {
      return this.exceptions;
    }
  }
}
