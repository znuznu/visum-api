package znu.visum.components.external.tmdb.infrastructure.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import znu.visum.components.external.tmdb.domain.errors.TmdbApiException;

public class ExternalApiErrorHandler {
  private static final Logger logger = LoggerFactory.getLogger(ExternalApiErrorHandler.class);

  public static RuntimeException buildTmdbException(WebClientResponseException exception) {
    logger.error(String.format("TMDB error occured: %s", exception.getMessage()));

    throw new TmdbApiException(
        exception.getRawStatusCode(), exception.getStatusText(), exception.getMessage());
  }
}
