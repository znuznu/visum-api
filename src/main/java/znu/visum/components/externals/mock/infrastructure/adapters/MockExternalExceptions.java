package znu.visum.components.externals.mock.infrastructure.adapters;

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
