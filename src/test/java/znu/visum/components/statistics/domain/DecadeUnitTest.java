package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDate;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DecadeUnitTest {

  @Test
  void shouldNotCreateWithNullValue() {
    assertThatThrownBy(() -> new Decade(null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field year");
  }

  @Test
  void shouldNotCreateWithNonRoundedValue() {
    assertThatThrownBy(() -> new Decade(Year.of(2003)))
        .isInstanceOf(InvalidDecadeException.class)
        .hasMessage("Invalid decade: 2003");
  }

  @Test
  void shouldCreateWithValue() {
    var decade = new Decade(Year.of(2000));

    assertThat(decade.year()).isEqualTo(Year.of(2000));
  }

  @Test
  void shouldReturnYearDay() {
    var decade = new Decade(Year.of(2000));

    assertThat(decade.yearDay()).isEqualTo(LocalDate.ofYearDay(2000, 1));
  }

  @Test
  void shouldReturnNextDecade() {
    var decade = new Decade(Year.of(2000));

    assertThat(decade.next()).isEqualTo(new Decade(Year.of(2010)));
  }
}
