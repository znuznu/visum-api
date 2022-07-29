package znu.visum.components.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.core.models.common.Pair;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class PerYearStatistics {

  private long reviewCount;
  private long movieCount;
  private int totalRuntimeInHours;
  private List<StatisticsMovie> highestRatedMoviesReleased;
  private List<StatisticsMovie> highestRatedOlderMovies;
  private List<Pair<String, Integer>> movieCountPerGenre;
}
