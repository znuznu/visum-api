package znu.visum.components.externals.tmdb.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

public class TmdbApiException extends VisumException {

  private TmdbApiException(final String message, final int statusCode) {
    super(
        "Message: " + message + " Status code: " + statusCode,
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "TMDB_API_EXCEPTION");
  }

  public static TmdbApiException withMessageAndStatusCode(String message, int statusCode) {
    return new TmdbApiException(message, statusCode);
  }
}
