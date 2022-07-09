package znu.visum.components.diary.domain;

import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.Month;

import static helpers.fixtures.DiaryEntryFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DiaryMonthUnitTest {

  @Test
  void shouldNotCreateWithoutMonth() {
    assertThatThrownBy(() -> DiaryMonth.of(null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field month");
  }

  @Test
  void shouldCreateWithEmptyDays() {
    var diaryMonth = DiaryMonth.of(Month.JANUARY);

    assertThat(diaryMonth.getDaysInDescendingOrder()).isEmpty();
  }

  @Test
  void shouldNotAddMovieWithoutCorrespondingMonth() {
    var diaryMonth = DiaryMonth.of(Month.JANUARY);

    assertThatThrownBy(() -> diaryMonth.addAll(february27Movie1Rewatch()))
        .isInstanceOf(InvalidDiaryMovieEntryDate.class)
        .hasMessage("Could not add entry with viewing date 1997-02-27 to a JANUARY diary month.");
  }

  @Test
  void shouldRetrieveMoviesInDescOrder() {
    var diaryMonth = DiaryMonth.of(Month.FEBRUARY);

    diaryMonth.addAll(
        february14Movie1FirstTimeSeen(),
        february14Movie4FirstTimeSeen(),
        february27Movie1Rewatch(),
        february14Movie4Rewatch());

    assertThat(diaryMonth.getDaysInDescendingOrder())
        .hasSize(4)
        .containsExactly(
            DiaryDay.of(february27Movie1Rewatch()),
            DiaryDay.of(february14Movie4Rewatch()),
            DiaryDay.of(february14Movie1FirstTimeSeen()),
            DiaryDay.of(february14Movie4FirstTimeSeen()));
  }
}
