package znu.visum.components.statistics.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class InvalidDecadeException extends VisumException {

  public InvalidDecadeException(int decade) {
    super("Invalid decade: " + decade);
  }
}
