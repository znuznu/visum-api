package znu.visum.components.accounts.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class MaximumAccountReachedException extends VisumException {
  public MaximumAccountReachedException() {
    super("Number of maximum account reached.");
  }
}
