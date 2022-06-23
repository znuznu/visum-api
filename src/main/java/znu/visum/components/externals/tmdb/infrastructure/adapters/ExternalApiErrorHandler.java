package znu.visum.components.externals.tmdb.infrastructure.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import znu.visum.components.externals.tmdb.domain.TmdbApiException;

public class ExternalApiErrorHandler {

  private static final Logger logger = LoggerFactory.getLogger(ExternalApiErrorHandler.class);

  private ExternalApiErrorHandler() {}

  public static TmdbApiException from(WebClientResponseException exception) {
    logger.error(String.format("TMDB error occured: %s", exception.getMessage()));

    throw TmdbApiException.withMessageAndStatusCode(
        exception.getMessage(), exception.getRawStatusCode());
  }
}
