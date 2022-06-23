package znu.visum.components.statistics.domain;

import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

public class InvalidDecadeException extends VisumException {

  public InvalidDecadeException(int decade) {
    super(
        "Invalid decade: " + decade, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "INVALID_DECADE");
  }
}
