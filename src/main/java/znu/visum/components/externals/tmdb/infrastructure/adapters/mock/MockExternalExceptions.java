package znu.visum.components.externals.tmdb.infrastructure.adapters.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class MockExternalExceptions {
  private RuntimeException searchMovies;

  public RuntimeException getSearchMoviesException() {
    return searchMovies;
  }
}
