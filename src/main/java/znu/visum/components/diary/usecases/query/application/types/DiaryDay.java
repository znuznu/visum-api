package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class DiaryDay {

  private Integer day;
  private DiaryMovieType movie;

  public static DiaryDay from(znu.visum.components.diary.domain.DiaryDay diaryDay) {
    return DiaryDay.builder()
        .day(diaryDay.getDay())
        .movie(DiaryMovieType.from(diaryDay.getMovie()))
        .build();
  }
}
