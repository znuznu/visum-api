package znu.visum.components.externals.tmdb.infrastructure.adapters.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

@AllArgsConstructor
@Builder
public class MockExternalResponses {
  VisumPage<ExternalMovieFromSearch> searchMovies;

  public VisumPage<ExternalMovieFromSearch> getSearchMoviesResponse() {
    return searchMovies;
  }
}
