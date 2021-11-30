package znu.visum.components.externals.tmdb.infrastructure.models;

public class TmdbSearchMoviesResponse extends TmdbSearchResponse<TmdbMovieFromSearch> {
  public TmdbSearchMoviesResponse() {}

  public TmdbSearchMoviesResponse(
      int page, int totalPages, int totalResults, TmdbMovieFromSearch[] results) {
    super(page, totalPages, totalResults, results);
  }
}
