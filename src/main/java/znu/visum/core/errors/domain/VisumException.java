package znu.visum.core.errors.domain;

import lombok.Getter;

@Getter
public class VisumException extends RuntimeException {

  private final VisumExceptionStatus status;
  private final String code;

  public VisumException(final String message, VisumExceptionStatus status, String code) {
    super(message);
    this.status = status;
    this.code = code;
  }
}
