package znu.visum.components.accounts.domain;

import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

public class MaximumAccountReachedException extends VisumException {
  public MaximumAccountReachedException() {
    super(
        "Number of maximum account reached.",
        VisumExceptionStatus.FORBIDDEN,
        "MAXIMUM_NUMBER_OF_ACCOUNT_REACHED");
  }
}
