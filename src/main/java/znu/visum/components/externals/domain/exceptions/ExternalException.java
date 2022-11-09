package znu.visum.components.externals.domain.exceptions;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

public class ExternalException extends VisumException {

  private ExternalException(final String message, final int statusCode) {
    super(
        "Message: " + message + " Status code: " + statusCode,
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "EXTERNAL_API_EXCEPTION");
  }

  public static ExternalException withMessageAndStatusCode(String message, int statusCode) {
    return new ExternalException(message, statusCode);
  }
}
