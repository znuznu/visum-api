package znu.visum.core.models.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LimitUnitTest {

  @Test
  void shouldNotCreateWithNegativeValue() {
    assertThatThrownBy(() -> new Limit(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Negative limit is not allowed.");
  }

  @Test
  void shouldCreateWithValue() {
    assertThat(new Limit(1).value()).isEqualTo(1);
  }
}
