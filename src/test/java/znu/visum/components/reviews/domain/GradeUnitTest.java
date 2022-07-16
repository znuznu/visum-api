package znu.visum.components.reviews.domain;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GradeUnitTest {

  @Test
  void shouldNotBuildWithOutOfRangeValue() {
    assertThatThrownBy(() -> Grade.of(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Grade -1 is out of range [1;10]");
    assertThatThrownBy(() -> Grade.of(11))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Grade 11 is out of range [1;10]");
  }

  @Test
  void shouldCreateWithValueInRange() {
    IntStream.rangeClosed(1, 10)
        .forEach(value -> assertThatNoException().isThrownBy(() -> Grade.of(value)));
  }
}
