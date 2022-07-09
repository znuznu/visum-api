package znu.visum.components.diary.usecases.query.application.types;

import lombok.Getter;
import znu.visum.components.diary.domain.Diary;
import znu.visum.core.models.common.Month;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DiaryYear {

  private static final List<Month> MONTHS =
      List.of(
          Month.DECEMBER,
          Month.NOVEMBER,
          Month.OCTOBER,
          Month.SEPTEMBER,
          Month.AUGUST,
          Month.JULY,
          Month.JUNE,
          Month.MAY,
          Month.APRIL,
          Month.MARCH,
          Month.FEBRUARY,
          Month.JANUARY);
  private final int year;
  private final List<DiaryMonth> months;

  private DiaryYear(int year, List<DiaryMonth> months) {
    this.months = months;
    this.year = year;
  }

  public static DiaryYear from(Diary diary) {
    int yearValue = diary.getYear().getValue();

    List<DiaryMonth> months =
        MONTHS.stream()
            .map(month -> DiaryMonth.from(diary.getMonths().get(Month.toJava(month))))
            .collect(Collectors.toUnmodifiableList());

    return new DiaryYear(yearValue, months);
  }
}
