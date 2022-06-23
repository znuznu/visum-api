package znu.visum.components.statistics.usecases.getperyear.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.statistics.domain.models.PerYearStatistics;
import znu.visum.components.statistics.domain.services.StatisticsService;
import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.List;

@Service
public class GetPerYearStatisticsService {
  private final StatisticsService statisticsService;

  @Autowired
  public GetPerYearStatisticsService(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  public PerYearStatistics getStatisticsForYear(Year year) {
    long reviewCount = this.statisticsService.getReviewCountOfTheYear(year);
    long movieCount = this.statisticsService.getMovieCountOfTheYear(year);
    List<Movie> highestRatedMoviesOfTheYear =
        this.statisticsService.findHighestRatedMoviesOfTheYear(year, 10);
    List<Movie> highestRatedOlderMovies =
        this.statisticsService.findHighestRatedDuringYearOlderMovies(year);
    int totalRuntimeInHours = this.statisticsService.getTotalRunningHoursOfTheYear(year);
    List<Pair<String, Integer>> movieCountPerGenre =
        this.statisticsService.getNumberOfMoviesOfTheYearPerGenre(year);

    return PerYearStatistics.builder()
        .reviewCount(reviewCount)
        .movieCount(movieCount)
        .highestRatedMoviesReleased(highestRatedMoviesOfTheYear)
        .highestRatedOlderMovies(highestRatedOlderMovies)
        .totalRuntimeInHours(totalRuntimeInHours)
        .movieCountPerGenre(movieCountPerGenre)
        .build();
  }
}
