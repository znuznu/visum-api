package znu.visum.components.diary.domain;

import org.junit.jupiter.api.Test;
import znu.visum.components.movies.domain.MovieDiaryFragment;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static helpers.fixtures.ViewingHistoryFixtures.viewingHistoryMovie1;
import static helpers.fixtures.ViewingHistoryFixtures.viewingHistoryMovie4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DiaryUnitTest {

  @Test
  void shouldNotCreateWithoutYear() {
    assertThatThrownBy(() -> new Diary(null, new ArrayList<>()))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field year");
  }

  @Test
  void shouldNotCreateWithNullMovies() {
    assertThatThrownBy(() -> new Diary(Year.of(1997), null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field movies");
  }

  @Test
  void shouldCreateWithEmptyMonths() {
    var diary = new Diary(Year.of(1997), new ArrayList<>());

    assertThat(diary.getJanuary().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getFebruary().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getMarch().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getApril().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getMay().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getJune().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getJuly().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getAugust().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getSeptember().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getOctober().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getNovember().getDaysInDescendingOrder()).isEmpty();
    assertThat(diary.getDecember().getDaysInDescendingOrder()).isEmpty();
  }

  @Test
  void shouldAddTheMoviesToTheCorrespondingMonths() {
    var diary = new Diary(Year.of(1997), movies());

    assertThat(diary.getFebruary().getDaysInDescendingOrder()).hasSize(4);
  }

  private List<MovieDiaryFragment> movies() {
    return List.of(
        MovieDiaryFragment.builder()
            .id(4L)
            .title("Movie 4")
            .viewingHistory(viewingHistoryMovie4())
            .releaseDate(LocalDate.of(1986, 10, 15))
            .posterUrl("https://movie4.io")
            .review(new MovieDiaryFragment.Review(5L, Grade.of(4)))
            .build(),
        MovieDiaryFragment.builder()
            .id(1L)
            .title("Movie 1")
            .viewingHistory(viewingHistoryMovie1())
            .releaseDate(LocalDate.of(1986, 10, 15))
            .posterUrl("https://movie1.io")
            .review(new MovieDiaryFragment.Review(1L, Grade.of(8)))
            .build());
  }
}
