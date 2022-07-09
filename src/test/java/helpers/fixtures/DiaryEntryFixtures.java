package helpers.fixtures;

import znu.visum.components.diary.domain.DiaryEntry;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;

public class DiaryEntryFixtures {

  public static DiaryEntry february14Movie4FirstTimeSeen() {
    return DiaryEntry.builder()
        .movieId(4L)
        .title("Movie 4")
        .isFavorite(true)
        .posterUrl("https://movie4.io")
        .releaseDate(LocalDate.of(1986, 10, 15))
        .review(new DiaryEntry.Review(5L, Grade.of(4)))
        .viewingDate(LocalDate.of(1997, 2, 14))
        .isRewatch(false)
        .build();
  }

  public static DiaryEntry february14Movie4Rewatch() {
    return DiaryEntry.builder()
        .movieId(4L)
        .title("Movie 4")
        .isFavorite(true)
        .posterUrl("https://movie4.io")
        .releaseDate(LocalDate.of(1986, 10, 15))
        .review(new DiaryEntry.Review(5L, Grade.of(4)))
        .viewingDate(LocalDate.of(1997, 2, 14))
        .isRewatch(true)
        .build();
  }

  public static DiaryEntry february14Movie1FirstTimeSeen() {
    return DiaryEntry.builder()
        .movieId(1L)
        .title("Movie 1")
        .isFavorite(true)
        .posterUrl("https://movie1.io")
        .releaseDate(LocalDate.of(1986, 10, 15))
        .review(new DiaryEntry.Review(1L, Grade.of(8)))
        .viewingDate(LocalDate.of(1997, 2, 14))
        .isRewatch(false)
        .build();
  }

  public static DiaryEntry february27Movie1Rewatch() {
    return DiaryEntry.builder()
        .movieId(1L)
        .title("Movie 1")
        .isFavorite(true)
        .posterUrl("https://movie1.io")
        .releaseDate(LocalDate.of(1986, 10, 15))
        .review(new DiaryEntry.Review(1L, Grade.of(8)))
        .viewingDate(LocalDate.of(1997, 2, 27))
        .isRewatch(true)
        .build();
  }
}
