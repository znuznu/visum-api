package znu.visum.components.externals.tmdb.infrastructure.models;

public class TmdbGetUpcomingMoviesResponse extends TmdbPageResponse<TmdbUpcomingMovie> {
  public TmdbGetUpcomingMoviesResponse() {}

  public TmdbGetUpcomingMoviesResponse(
      int page, int totalPages, int totalResults, TmdbUpcomingMovie[] results) {
    super(page, totalPages, totalResults, results);
  }
}
