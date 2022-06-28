package znu.visum.components.reviews.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.InvalidStringException;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

class ContentUnitTest {

  @Test
  void shouldNotCreateWithNullText() {
    Assertions.assertThatThrownBy(() -> Content.of(null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field text");
  }

  @Test
  void shouldNotCreateWithBlankText() {
    Assertions.assertThatThrownBy(() -> Content.of("    "))
        .isInstanceOf(InvalidStringException.class)
        .hasMessage("Blank field text");
  }
}
