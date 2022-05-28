package znu.visum.components.statistics.usecases.getalltime.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.statistics.domain.models.AllTimeStatistics;
import znu.visum.components.statistics.domain.models.MovieCount;
import znu.visum.components.statistics.domain.services.StatisticsService;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GetAllTimeStatisticsServiceImpl implements GetAllTimeStatisticsService {
  private final StatisticsService statisticsService;

  @Autowired
  public GetAllTimeStatisticsServiceImpl(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @Override
  public AllTimeStatistics getAllTimeStatistics() {
    LocalDate start = LocalDate.ofYearDay(1900, 1);
    LocalDate end = LocalDate.ofYearDay(2100, 1);

    int totalRuntimeInHours = this.statisticsService.getTotalRunningHoursBetween(start, end);

    List<Pair<Integer, Float>> averageRatePerYear =
        this.statisticsService.getRatedMoviesAveragePerYearBetween(start, end);

    long reviewCount = this.statisticsService.getReviewCount();

    List<Pair<Integer, Integer>> movieCountPerYear =
        this.statisticsService.getNumberOfMoviesPerYearBetween(start, end);
    List<Pair<String, Integer>> movieCountPerGenre =
        this.statisticsService.getNumberOfMoviesPerGenreBetween(start, end);
    List<Pair<String, Integer>> movieCountPerOriginalLanguage =
        this.statisticsService.getNumberOfMoviesPerOriginalLanguageBetween(start, end);
    MovieCount movieCount =
        new MovieCount(movieCountPerYear, movieCountPerGenre, movieCountPerOriginalLanguage);

    List<Pair<Integer, List<Movie>>> highestRatedMoviesPerDecade =
        IntStream.range(1900, 2050)
            .filter(year -> year % 10 == 0)
            .mapToObj(decade -> this.statisticsService.getHighestRatedMoviesForDecade(decade, 5))
            .filter(pair -> !pair.getValue().isEmpty())
            .collect(Collectors.toList());

    return AllTimeStatistics.builder()
        .reviewCount(reviewCount)
        .averageRatePerYear(averageRatePerYear)
        .highestRatedMoviesPerDecade(highestRatedMoviesPerDecade)
        .movieCount(movieCount)
        .totalRuntimeInHours(totalRuntimeInHours)
        .build();
  }
}
