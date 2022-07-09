package znu.visum.components.diary.domain;

import org.junit.jupiter.api.Test;

import static helpers.fixtures.DiaryEntryFixtures.february27Movie1Rewatch;
import static org.assertj.core.api.Assertions.assertThat;

class DiaryDayUnitTest {

  @Test
  void shouldCreate() {
    var diaryDay = DiaryDay.of(february27Movie1Rewatch());

    assertThat(diaryDay.getDay()).isEqualTo(27);
    assertThat(diaryDay.getMovie()).isEqualTo(february27Movie1Rewatch());
  }
}
