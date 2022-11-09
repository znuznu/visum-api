package znu.visum.components.externals.domain.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import znu.visum.components.externals.domain.models.ExternalApi;

public class ExternalApiExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ExternalApiExceptionHandler.class);

  private ExternalApiExceptionHandler() {}

  public static ExternalException from(ExternalApi api, WebClientResponseException exception) {
    logger.error(
        String.format("External API (%s) error occurred: %s", api, exception.getMessage()));

    throw ExternalException.withMessageAndStatusCode(
        exception.getMessage(), exception.getRawStatusCode());
  }
}
