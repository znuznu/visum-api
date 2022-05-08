package znu.visum.components.statistics.domain.errors;

import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

public class StatisticsDateRangeException extends VisumException {
  public StatisticsDateRangeException() {
    super(
        "Invalid statistics date range.",
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "INVALID_STATISTICS_DATE_RANGE");
  }
}
