package znu.visum.components.external.tmdb.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class TmdbApiException extends VisumException {
  private int statusCode;
  private String status;

  private TmdbApiException() {
    super("Default TmdbApiException.");
  }

  public TmdbApiException(int statusCode, String status, String message) {
    super(message);
    this.statusCode = statusCode;
    this.status = status;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getStatus() {
    return status;
  }

  public static final class Builder {
    private final TmdbApiException tmdbApiException;

    public Builder() {
      this.tmdbApiException = new TmdbApiException();
    }

    public Builder statusCode(int statusCode) {
      this.tmdbApiException.statusCode = statusCode;
      return this;
    }

    public Builder status(String status) {
      this.tmdbApiException.status = status;
      return this;
    }

    public TmdbApiException build() {
      return this.tmdbApiException;
    }
  }
}
