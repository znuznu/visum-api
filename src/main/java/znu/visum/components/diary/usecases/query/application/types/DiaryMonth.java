package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.core.models.common.Month;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class DiaryMonth {
  private Month month;
  private List<DiaryDay> days;

  public static DiaryMonth from(znu.visum.components.diary.domain.DiaryMonth diaryMonth) {
    return DiaryMonth.builder()
        .month(Month.from(diaryMonth.getMonth()))
        .days(diaryMonth.getDaysInDescendingOrder().stream().map(DiaryDay::from).toList())
        .build();
  }
}
