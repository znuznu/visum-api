package znu.visum.core.exceptions.domain;

import lombok.Getter;

@Getter
public abstract class VisumException extends RuntimeException {

  private final VisumExceptionStatus status;
  private final String code;

  protected VisumException(final String message, VisumExceptionStatus status, String code) {
    super(message);
    this.status = status;
    this.code = code;
  }
}
