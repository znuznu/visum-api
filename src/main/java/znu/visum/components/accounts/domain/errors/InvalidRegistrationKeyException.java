package znu.visum.components.accounts.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class InvalidRegistrationKeyException extends VisumException {
  public InvalidRegistrationKeyException() {
    super("Invalid registration key.");
  }
}
