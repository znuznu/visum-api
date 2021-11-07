package znu.visum.components.external.tmdb.infrastructure.validators;

import znu.visum.components.external.domain.models.ExternalApi;
import znu.visum.components.external.tmdb.infrastructure.models.TmdbGetMovieByIdResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbGetMovieByIdValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbGetMovieByIdResponse, TmdbGetMovieByIdResponseValidator> {
  public TmdbGetMovieByIdValidationHandler() {
    super(
        TmdbGetMovieByIdResponse.class, new TmdbGetMovieByIdResponseValidator(), ExternalApi.TMDB);
  }
}
