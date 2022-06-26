package znu.visum.components.statistics.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

public class StatisticsDateRangeException extends VisumException {
  public StatisticsDateRangeException() {
    super(
        "Invalid statistics date range.",
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "INVALID_STATISTICS_DATE_RANGE");
  }
}
