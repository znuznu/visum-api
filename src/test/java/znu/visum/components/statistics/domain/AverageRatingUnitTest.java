package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AverageRatingUnitTest {

  @Test
  void shouldNotCreateWithInvalidRating() {
    assertThatThrownBy(() -> new AverageRating(0.9f))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Average rating should be between [1.0,10.0]");
    assertThatThrownBy(() -> new AverageRating(10.1f))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Average rating should be between [1.0,10.0]");
  }

  @Test
  void shouldCreate() {
    assertThat(new AverageRating(3.4f).rating()).isEqualTo(3.4f);
  }
}
