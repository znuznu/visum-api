package znu.visum.components.externals.tmdb.infrastructure.validators.upcomingmovies;

import znu.visum.components.externals.domain.models.ExternalApi;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetUpcomingMoviesResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbGetUpcomingMoviesResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbGetUpcomingMoviesResponse, TmdbGetUpcomingMoviesResponseValidator> {
  public TmdbGetUpcomingMoviesResponseBodyValidationHandler() {
    super(
        TmdbGetUpcomingMoviesResponse.class,
        new TmdbGetUpcomingMoviesResponseValidator(),
        ExternalApi.TMDB);
  }
}
