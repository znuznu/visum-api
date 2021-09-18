package znu.visum.components.external.tmdb.infrastructure.validators;

import znu.visum.components.external.domain.models.ExternalApi;
import znu.visum.components.external.tmdb.infrastructure.models.TmdbSearchMoviesResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbSearchMoviesResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbSearchMoviesResponse, TmdbSearchMoviesResponseValidator> {
  public TmdbSearchMoviesResponseBodyValidationHandler() {
    super(
        TmdbSearchMoviesResponse.class, new TmdbSearchMoviesResponseValidator(), ExternalApi.TMDB);
  }
}
