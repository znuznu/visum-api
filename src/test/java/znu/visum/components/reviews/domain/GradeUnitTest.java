package znu.visum.components.reviews.domain;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GradeUnitTest {

  @Test
  void shouldThrowOutOfRange() {
    assertThatThrownBy(() -> new Grade(-1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Grade(11)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldCreateGradeWithValueInRange() {
    IntStream.range(1, 11)
        .forEach(value -> assertThatNoException().isThrownBy(() -> new Grade(value)));
  }
}
