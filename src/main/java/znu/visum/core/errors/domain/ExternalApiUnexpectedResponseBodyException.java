package znu.visum.core.errors.domain;

import znu.visum.components.external.domain.models.ExternalApi;

public class ExternalApiUnexpectedResponseBodyException extends VisumException {
  private final ExternalApi externalApi;

  public ExternalApiUnexpectedResponseBodyException(String message, ExternalApi externalApi) {
    super(String.format("Invalid response from %s API: %s", externalApi, message));
    this.externalApi = externalApi;
  }

  public ExternalApi getExternalApi() {
    return externalApi;
  }
}
