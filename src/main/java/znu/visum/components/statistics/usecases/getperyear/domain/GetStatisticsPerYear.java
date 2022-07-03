package znu.visum.components.statistics.usecases.getperyear.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.statistics.domain.PerYearStatistics;
import znu.visum.components.statistics.domain.StatisticsService;
import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.List;

@Service
public class GetStatisticsPerYear {

  private final StatisticsService statisticsService;

  @Autowired
  public GetStatisticsPerYear(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  public PerYearStatistics process(Year year) {
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
