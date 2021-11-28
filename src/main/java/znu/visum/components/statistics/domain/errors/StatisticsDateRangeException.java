package znu.visum.components.statistics.domain.errors;

import znu.visum.core.errors.domain.VisumException;

public class StatisticsDateRangeException extends VisumException {
  public StatisticsDateRangeException() {
    super("Invalid statistics date range.");
  }

  public StatisticsDateRangeException(String message) {
    super(message);
  }
}
