package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Year;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class DiaryFilters {
  private final Year year;
  private final Integer grade;
  private final Long genreId;
}
