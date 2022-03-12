package znu.visum.components.diary.usecases.query.application.types;

import znu.visum.core.models.common.Month;

import java.util.List;

public class DiaryMonth {
  private Month month;

  private List<DiaryDay> days;

  public DiaryMonth() {}

  public DiaryMonth(Month month, List<DiaryDay> days) {
    this.month = month;
    this.days = days;
  }

  public Month getMonth() {
    return month;
  }

  public void setMonth(Month month) {
    this.month = month;
  }

  public List<DiaryDay> getDays() {
    return days;
  }

  public void setDays(List<DiaryDay> days) {
    this.days = days;
  }

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

  public static class Builder {
    private Month month;

    private List<DiaryDay> days;

    public DiaryMonth build() {
      DiaryMonth result = new DiaryMonth();
      result.month = this.month;
      result.days = this.days;
      return result;
    }

    public DiaryMonth.Builder month(Month month) {
      this.month = month;
      return this;
    }

    public DiaryMonth.Builder days(List<DiaryDay> days) {
      this.days = days;
      return this;
    }
  }
}
