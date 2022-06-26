package znu.visum.core.exceptions.domain;

import znu.visum.components.externals.domain.ExternalApi;

public class ExternalApiUnexpectedResponseBodyException extends VisumException {

  private ExternalApiUnexpectedResponseBodyException(String message, ExternalApi externalApi) {
    super(
        String.format("Invalid response from %s API: %s", externalApi, message),
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "UNEXPECTED_EXTERNAL_RESPONSE");
  }

  public static ExternalApiUnexpectedResponseBodyException withMessageForApi(
      String message, ExternalApi api) {
    return new ExternalApiUnexpectedResponseBodyException(message, api);
  }
}
