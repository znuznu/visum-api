package znu.visum.components.statistics.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

import java.time.LocalDate;

public class StatisticsDateRangeException extends VisumException {

  private StatisticsDateRangeException(LocalDate startDate, LocalDate endDate) {
    super(
        "Start date " + startDate + " is after end date " + endDate,
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "INVALID_STATISTICS_DATE_RANGE");
  }

  public static StatisticsDateRangeException of(LocalDate startDate, LocalDate endDate) {
    return new StatisticsDateRangeException(startDate, endDate);
  }
}
