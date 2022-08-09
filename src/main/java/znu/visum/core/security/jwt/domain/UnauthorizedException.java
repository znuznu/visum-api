package znu.visum.core.security.jwt.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

public abstract class UnauthorizedException extends VisumException {

  protected UnauthorizedException(String message, String status) {
    super(message, VisumExceptionStatus.UNAUTHORIZED, status);
  }
}
