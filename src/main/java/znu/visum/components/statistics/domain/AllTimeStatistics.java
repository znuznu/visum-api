package znu.visum.components.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.Movie;
import znu.visum.core.models.common.Pair;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class AllTimeStatistics {

  private int totalRuntimeInHours;
  private AverageRatingPerYear averageRatingPerYear;
  private long reviewCount;
  private MovieCount movieCount;
  private List<Pair<Decade, List<Movie>>> highestRatedMoviesPerDecade;
}
