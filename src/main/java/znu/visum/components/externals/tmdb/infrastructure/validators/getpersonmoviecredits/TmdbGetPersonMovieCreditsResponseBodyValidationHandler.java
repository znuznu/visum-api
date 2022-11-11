package znu.visum.components.externals.tmdb.infrastructure.validators.getpersonmoviecredits;

import znu.visum.components.externals.domain.models.ExternalApi;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetPersonMovieCreditsResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbGetPersonMovieCreditsResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbGetPersonMovieCreditsResponse, TmdbGetPersonMovieCreditsResponseValidator> {
  public TmdbGetPersonMovieCreditsResponseBodyValidationHandler() {
    super(
        TmdbGetPersonMovieCreditsResponse.class,
        new TmdbGetPersonMovieCreditsResponseValidator(),
        ExternalApi.TMDB);
  }
}
