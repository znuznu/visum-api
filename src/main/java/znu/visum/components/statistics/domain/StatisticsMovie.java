package znu.visum.components.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class StatisticsMovie {

  private final long id;
  private final String title;
  private final LocalDate releaseDate;
  private final Grade grade;
  private final String posterUrl;
}
