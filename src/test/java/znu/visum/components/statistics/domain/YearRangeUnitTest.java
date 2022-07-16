package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDate;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YearRangeUnitTest {

  @Test
  void shouldNotCreateWithNullYear() {
    assertThatThrownBy(() -> new YearRange(null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field year");
  }

  @Test
  void shouldReturnEndDateOfTheYear() {
    var range = new YearRange(Year.of(1997));

    assertThat(range.inclusiveEndDate()).isEqualTo(LocalDate.of(1997, 12, 31));
  }

  @Test
  void shouldReturnFirstDateOfTheNextYear() {
    var range = new YearRange(Year.of(1997));

    assertThat(range.exclusiveEndDate()).isEqualTo(LocalDate.of(1998, 1, 1));
  }
}
