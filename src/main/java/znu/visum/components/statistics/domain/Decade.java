package znu.visum.components.statistics.domain;

import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDate;
import java.time.Year;

public record Decade(Year year) {

    public Decade {
        VisumAssert.notNull("year", year);

        if (year.getValue() % 10 != 0) {
            throw new InvalidDecadeException(year.getValue());
        }
    }

    public LocalDate yearDay() {
        return LocalDate.ofYearDay(year.getValue(), 1);
    }

    public Decade next() {
        return new Decade(year.plusYears(10));
    }
}
