package znu.visum.components.statistics.domain;

import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDate;
import java.time.Year;

public record YearRange(Year year) {

    public YearRange {
        VisumAssert.notNull("year", year);
    }

    public LocalDate startDate() {
        return LocalDate.of(year.getValue(), 1, 1);
    }

    public LocalDate exclusiveEndDate()  {
        return LocalDate.of(year.getValue() + 1, 1, 1);
    }

    public LocalDate inclusiveEndDate()  {
        return LocalDate.of(year.getValue(), 12, 31);
    }
}
