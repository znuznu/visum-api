package znu.visum.components.externals.tmdb.infrastructure.validators.configuration;

import znu.visum.components.externals.domain.ExternalApi;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetConfigurationResponse;
import znu.visum.core.validators.infrastructure.AbstractExternalResponseBodyValidationHandler;

public class TmdbGetConfigurationResponseBodyValidationHandler
    extends AbstractExternalResponseBodyValidationHandler<
        TmdbGetConfigurationResponse, TmdbGetConfigurationResponseValidator> {
  public TmdbGetConfigurationResponseBodyValidationHandler() {
    super(
        TmdbGetConfigurationResponse.class,
        new TmdbGetConfigurationResponseValidator(),
        ExternalApi.TMDB);
  }
}
