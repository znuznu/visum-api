package znu.visum.components.statistics.usecases.getperyear.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.reviews.domain.ReviewRepository;
import znu.visum.components.statistics.domain.DateRange;
import znu.visum.components.statistics.domain.PerYearStatistics;
import znu.visum.components.statistics.domain.StatisticsMovie;
import znu.visum.components.statistics.domain.YearRange;
import znu.visum.core.models.common.Limit;
import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.List;

@Service
public class GetStatisticsPerYear {

  private final MovieQueryRepository movieQueryRepository;

  private final ReviewRepository reviewRepository;

  @Autowired
  public GetStatisticsPerYear(
      MovieQueryRepository movieQueryRepository, ReviewRepository reviewRepository) {
    this.movieQueryRepository = movieQueryRepository;
    this.reviewRepository = reviewRepository;
  }

  public PerYearStatistics process(Year year) {
    long reviewCount = this.reviewRepository.countUpdated(year);
    long movieCount = this.movieQueryRepository.countByReleaseYear(year);
    int totalRuntimeInHours = this.getTotalRunningHoursOfTheYear(year);
    List<StatisticsMovie> highestRatedMoviesOfTheYear =
        this.findHighestRatedMoviesOfTheYear(year, new Limit(10));
    List<StatisticsMovie> highestRatedOlderMovies =
        this.movieQueryRepository.findHighestRatedDuringYearOlderMovies(year);
    List<Pair<String, Integer>> movieCountPerGenre = this.getMovieCountOfTheYearPerGenre(year);

    return PerYearStatistics.builder()
        .reviewCount(reviewCount)
        .movieCount(movieCount)
        .totalRuntimeInHours(totalRuntimeInHours)
        .highestRatedMoviesReleased(highestRatedMoviesOfTheYear)
        .highestRatedOlderMovies(highestRatedOlderMovies)
        .movieCountPerGenre(movieCountPerGenre)
        .build();
  }

  private int getTotalRunningHoursOfTheYear(Year year) {
    var yearRange = new YearRange(year);
    var dateRange = new DateRange(yearRange.startDate(), yearRange.exclusiveEndDate());

    return this.movieQueryRepository.getTotalRunningHoursBetween(dateRange);
  }

  private List<Pair<String, Integer>> getMovieCountOfTheYearPerGenre(Year year) {
    var yearRange = new YearRange(year);
    var dateRange = new DateRange(yearRange.startDate(), yearRange.exclusiveEndDate());

    return this.movieQueryRepository.getMovieCountPerGenreBetween(dateRange);
  }

  private List<StatisticsMovie> findHighestRatedMoviesOfTheYear(Year year, Limit limit) {
    var yearRange = new YearRange(year);
    var dateRange = new DateRange(yearRange.startDate(), yearRange.exclusiveEndDate());

    return this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(dateRange, limit);
  }
}
