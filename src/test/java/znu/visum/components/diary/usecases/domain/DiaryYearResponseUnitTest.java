package znu.visum.components.diary.usecases.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.diary.usecases.query.application.types.DiaryMonth;
import znu.visum.components.diary.usecases.query.application.types.DiaryYear;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.core.models.common.Month;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DiaryYearResponseUnitTest {

  @DisplayName("from() - it should return movies by month, sorted by day (desc)")
  @Test
  void itShouldMapMoviesToADiaryResponse() {
    DiaryMovie movie1 =
        DiaryMovie.builder()
            .id(1L)
            .title("Mulholland Drive")
            .releaseDate(LocalDate.of(2001, 10, 12))
            .isFavorite(true)
            .isRewatch(true)
            .review(null)
            .posterUrl("mulho.jpeg")
            .viewingDate(LocalDate.of(2019, 1, 1))
            .build();

    DiaryMovie movie2 =
        DiaryMovie.builder()
            .id(1L)
            .title("Mulholland Drive")
            .releaseDate(LocalDate.of(2001, 10, 12))
            .posterUrl("mulho.jpeg")
            .isFavorite(true)
            .isRewatch(true)
            .review(null)
            .viewingDate(LocalDate.of(2019, 1, 5))
            .build();

    DiaryMovie movie3 =
        DiaryMovie.builder()
            .id(2L)
            .title("Star Wars V")
            .posterUrl("sw5.jpeg")
            .releaseDate(LocalDate.of(1980, 2, 6))
            .isFavorite(false)
            .isRewatch(false)
            .review(ReviewFromMovie.builder().id(111L).movieId(2L).grade(Grade.of(10)).build())
            .viewingDate(LocalDate.of(2019, 10, 8))
            .build();

    DiaryMovie movie4 =
        DiaryMovie.builder()
            .id(3L)
            .title("Avatar")
            .posterUrl("avatar.jpeg")
            .releaseDate(LocalDate.of(2012, 5, 14))
            .isFavorite(true)
            .isRewatch(false)
            .review(null)
            .viewingDate(LocalDate.of(2019, 10, 19))
            .build();

    DiaryMovie movie5 =
        DiaryMovie.builder()
            .id(3L)
            .title("Avatar")
            .posterUrl("avatar.jpeg")
            .releaseDate(LocalDate.of(2012, 5, 14))
            .isFavorite(true)
            .isRewatch(false)
            .review(null)
            .viewingDate(LocalDate.of(2019, 10, 14))
            .build();

    List<DiaryMovie> movies = List.of(movie1, movie2, movie3, movie4, movie5);

    DiaryYear diaryYear = DiaryYear.from(Year.of(2019), movies);
    DiaryMonth january = diaryYear.getMonths().get(11);
    DiaryMonth october = diaryYear.getMonths().get(2);

    assertThat(diaryYear.getYear()).isEqualTo(2019);
    assertThat(january.getMonth()).isEqualTo(Month.JANUARY);
    assertThat(january.getDays().size()).isEqualTo(2);
    assertThat(january.getDays().get(0).getDay()).isEqualTo(5);
    assertThat(january.getDays().get(1).getDay()).isOne();

    assertThat(october.getMonth()).isEqualTo(Month.OCTOBER);
    assertThat(october.getDays().size()).isEqualTo(3);
    assertThat(october.getDays().get(0).getDay()).isEqualTo(19);
    assertThat(october.getDays().get(1).getDay()).isEqualTo(14);
    assertThat(october.getDays().get(2).getDay()).isEqualTo(8);
  }
}
