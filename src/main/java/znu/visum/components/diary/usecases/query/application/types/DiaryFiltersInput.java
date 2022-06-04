package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiaryFiltersInput {

  private int year;
  private Long genreId;
  private Integer grade;

  public DiaryFiltersInput() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryFiltersInput that = (DiaryFiltersInput) o;
    return year == that.year
        && java.util.Objects.equals(genreId, that.genreId)
        && java.util.Objects.equals(grade, that.grade);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(year, genreId, grade);
  }
}
