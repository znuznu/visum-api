package znu.visum.components.statistics.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.models.common.Pair;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class AllTimeStatistics {

  private int totalRuntimeInHours;
  private List<Pair<Integer, Float>> averageRatePerYear;
  private long reviewCount;
  private MovieCount movieCount;
  private List<Pair<Integer, List<Movie>>> highestRatedMoviesPerDecade;
}
