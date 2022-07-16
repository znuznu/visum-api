package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateRangeUnitTest {

  @Test
  void shouldNotCreateWithNullDates() {
    assertThatThrownBy(() -> new DateRange(null, LocalDate.of(1997, 1, 1)))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field startDate");

    assertThatThrownBy(() -> new DateRange(LocalDate.of(1997, 1, 1), null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field endDate");
  }

  @Test
  void shouldNotCreateWithStartAfterEnd() {
    assertThatThrownBy(() -> new DateRange(LocalDate.of(1997, 1, 1), LocalDate.of(1996, 1, 1)))
        .isInstanceOf(StatisticsDateRangeException.class)
        .hasMessage(
            "Start date "
                + LocalDate.of(1997, 1, 1)
                + " is after end date "
                + LocalDate.of(1996, 1, 1));
  }

  @Test
  void shouldCreate() {
    var dateRange = new DateRange(LocalDate.of(1996, 1, 1), LocalDate.of(1997, 1, 1));

    assertThat(dateRange.startDate()).isEqualTo(LocalDate.of(1996, 1, 1));
    assertThat(dateRange.endDate()).isEqualTo(LocalDate.of(1997, 1, 1));
  }
}
