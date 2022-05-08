package znu.visum.core.errors.domain;

import znu.visum.components.externals.domain.models.ExternalApi;

public class ExternalApiUnexpectedResponseBodyException extends VisumException {

  private final ExternalApi externalApi;

  public ExternalApiUnexpectedResponseBodyException(String message, ExternalApi externalApi) {
    super(
        String.format("Invalid response from %s API: %s", externalApi, message),
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "UNEXPECTED_EXTERNAL_RESPONSE");
    this.externalApi = externalApi;
  }

  public ExternalApi getExternalApi() {
    return externalApi;
  }
}
