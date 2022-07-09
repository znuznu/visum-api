package znu.visum.components.diary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class DiaryDay {

  private final int day;
  private final DiaryEntry movie;

  private DiaryDay(int day, DiaryEntry movie) {
    this.day = day;
    this.movie = movie;
  }

  public static DiaryDay of(DiaryEntry movie) {
    int day = movie.getViewingDate().getDayOfMonth();

    return new DiaryDay(day, movie);
  }
}
