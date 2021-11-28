package znu.visum.components.statistics.usecases.getperyear.domain;

import znu.visum.components.statistics.domain.models.PerYearStatistics;

import java.time.Year;

public interface GetPerYearStatisticsService {
  PerYearStatistics getStatisticsForYear(Year year);
}
