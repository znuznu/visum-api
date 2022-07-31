package znu.visum.components.externals.tmdb.infrastructure.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TmdbNowPlayingMoviesResponse extends TmdbPageResponse<TmdbNowPlayingMovie> {

  public TmdbNowPlayingMoviesResponse(
      int page, int totalPages, int totalResults, TmdbNowPlayingMovie[] results) {
    super(page, totalPages, totalResults, results);
  }
}
