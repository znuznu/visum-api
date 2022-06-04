package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.core.models.common.Month;

import java.util.List;

@AllArgsConstructor
@Getter
public class DiaryMonth {
  private Month month;
  private List<DiaryDay> days;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryMonth that = (DiaryMonth) o;
    return java.util.Objects.equals(month, that.month) && java.util.Objects.equals(days, that.days);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(month, days);
  }
}
