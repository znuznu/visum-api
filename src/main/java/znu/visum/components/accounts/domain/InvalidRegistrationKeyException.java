package znu.visum.components.accounts.domain;

import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

public class InvalidRegistrationKeyException extends VisumException {
  public InvalidRegistrationKeyException() {
    super(
        "Invalid registration key.", VisumExceptionStatus.UNAUTHORIZED, "INVALID_REGISTRATION_KEY");
  }
}
