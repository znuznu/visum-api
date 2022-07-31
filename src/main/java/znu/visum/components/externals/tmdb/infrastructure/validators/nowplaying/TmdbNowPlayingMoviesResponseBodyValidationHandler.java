package znu.visum.components.externals.tmdb.infrastructure.validators.nowplaying;

import znu.visum.components.externals.domain.ExternalApi;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbNowPlayingMoviesResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbNowPlayingMoviesResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbNowPlayingMoviesResponse, TmdbNowPlayingMoviesResponseValidator> {
  public TmdbNowPlayingMoviesResponseBodyValidationHandler() {
    super(
        TmdbNowPlayingMoviesResponse.class,
        new TmdbNowPlayingMoviesResponseValidator(),
        ExternalApi.TMDB);
  }
}
