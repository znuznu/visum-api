package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiaryDay {

  private Integer day;
  private DiaryMovieType movie;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryDay that = (DiaryDay) o;
    return java.util.Objects.equals(day, that.day) && java.util.Objects.equals(movie, that.movie);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(day, movie);
  }
}
