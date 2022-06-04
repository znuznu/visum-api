package znu.visum.components.externals.tmdb.infrastructure.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TmdbSearchMoviesResponse extends TmdbPageResponse<TmdbMovieFromSearch> {

  public TmdbSearchMoviesResponse(
      int page, int totalPages, int totalResults, TmdbMovieFromSearch[] results) {
    super(page, totalPages, totalResults, results);
  }
}
