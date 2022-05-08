package znu.visum.components.externals.tmdb.domain.errors;

import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

public class TmdbApiException extends VisumException {
  private final int statusCode;
  private final String message;

  public TmdbApiException(final String message, final int statusCode) {
    super(message, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "TMDB_API_EXCEPTION");
    this.message = message;
    this.statusCode = statusCode;
  }
}
