package znu.visum.components.externals.tmdb.infrastructure.adapters.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class TmdbInMemoryExceptions {
  private RuntimeException searchMovies;

  public RuntimeException getSearchMoviesException() {
    return searchMovies;
  }
}
