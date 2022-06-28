package znu.visum.core.assertions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.InvalidStringException;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDate;

class VisumAssertUnitTest {

  @Nested
  class NotNull {

    @Test
    void shouldNotValidateNullObject() {
      Assertions.assertThatThrownBy(() -> VisumAssert.notNull("myField", null))
          .isExactlyInstanceOf(MissingMandatoryFieldException.class)
          .hasMessage("Missing mandatory field myField");
    }

    @Test
    void shouldValidateNonNullObject() {
      Assertions.assertThatCode(() -> VisumAssert.notNull("myField", LocalDate.ofYearDay(100, 1)))
          .doesNotThrowAnyException();
    }
  }

  @Nested
  class NotBlank {

    @Test
    void shouldNotValidateBlankObject() {
      Assertions.assertThatThrownBy(() -> VisumAssert.notBlank("myField", "       "))
          .isExactlyInstanceOf(InvalidStringException.class)
          .hasMessage("Blank field myField");
    }

    @Test
    void shouldValidateNonBlankObject() {
      Assertions.assertThatCode(() -> VisumAssert.notBlank("myField", "some cool text"))
          .doesNotThrowAnyException();
    }
  }
}
