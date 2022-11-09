package znu.visum.components.externals.tmdb.infrastructure.validators.moviebyid;

import znu.visum.components.externals.domain.models.ExternalApi;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetMovieByIdResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbGetMovieByIdResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbGetMovieByIdResponse, TmdbGetMovieByIdResponseValidator> {
  public TmdbGetMovieByIdResponseBodyValidationHandler() {
    super(
        TmdbGetMovieByIdResponse.class, new TmdbGetMovieByIdResponseValidator(), ExternalApi.TMDB);
  }
}
