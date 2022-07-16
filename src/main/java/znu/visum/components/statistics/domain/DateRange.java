package znu.visum.components.statistics.domain;

import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDate;

public record DateRange(LocalDate startDate, LocalDate endDate) {

    public DateRange {
        VisumAssert.notNull("startDate", startDate);
        VisumAssert.notNull("endDate", endDate);

        if (startDate.isAfter(endDate)) {
            throw StatisticsDateRangeException.of(startDate, endDate);
        }
    }
}
