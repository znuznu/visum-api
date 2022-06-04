package znu.visum.components.externals.tmdb.infrastructure.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TmdbGetUpcomingMoviesResponse extends TmdbPageResponse<TmdbUpcomingMovie> {

  public TmdbGetUpcomingMoviesResponse(
      int page, int totalPages, int totalResults, TmdbUpcomingMovie[] results) {
    super(page, totalPages, totalResults, results);
  }
}
