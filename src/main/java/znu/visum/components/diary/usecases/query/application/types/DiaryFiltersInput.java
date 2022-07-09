package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DiaryFiltersInput {

  private int year;
  private Long genreId;
  private Integer grade;
}
