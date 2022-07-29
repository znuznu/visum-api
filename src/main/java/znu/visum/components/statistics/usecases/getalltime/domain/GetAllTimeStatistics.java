package znu.visum.components.statistics.usecases.getalltime.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.reviews.domain.ReviewRepository;
import znu.visum.components.statistics.domain.*;
import znu.visum.core.models.common.Limit;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GetAllTimeStatistics {

  public static final int START_DECADE = 1900;
  public static final int END_DECADE = 2050;
  private static final DateRange ALL_TIME_RANGE =
      new DateRange(LocalDate.ofYearDay(1900, 1), LocalDate.ofYearDay(2100, 1));

  private final MovieQueryRepository movieQueryRepository;
  private final ReviewRepository reviewRepository;

  @Autowired
  public GetAllTimeStatistics(
      MovieQueryRepository movieQueryRepository, ReviewRepository reviewRepository) {
    this.movieQueryRepository = movieQueryRepository;
    this.reviewRepository = reviewRepository;
  }

  public AllTimeStatistics process() {
    int totalRuntimeInHours = this.getAllTimeRuntimeHours();
    long reviewCount = this.reviewRepository.count();
    var averageRatePerYear = this.getAllTimeRatedMoviesAveragePerYear();
    var movieCount = this.getAllTimeMovieCount();
    var highestRatedMoviesPerDecade = this.getHighestRatedMoviesPerDecade();

    return AllTimeStatistics.builder()
        .reviewCount(reviewCount)
        .averageRatingPerYear(averageRatePerYear)
        .highestRatedMoviesPerDecade(highestRatedMoviesPerDecade)
        .movieCount(movieCount)
        .totalRuntimeInHours(totalRuntimeInHours)
        .build();
  }

  private int getAllTimeRuntimeHours() {
    return movieQueryRepository.getTotalRunningHoursBetween(ALL_TIME_RANGE);
  }

  private MovieCount getAllTimeMovieCount() {
    var movieCountPerYear = this.getAllTimeMovieCountPerYear();
    var movieCountPerGenre = this.getAllTimeMovieCountPerGenre();
    var movieCountPerOriginalLanguage = this.getAllTimeMovieCountPerOriginalLanguage();

    return new MovieCount(movieCountPerYear, movieCountPerGenre, movieCountPerOriginalLanguage);
  }

  private List<Pair<Year, Integer>> getAllTimeMovieCountPerYear() {
    return movieQueryRepository.getMovieCountPerYearBetween(ALL_TIME_RANGE);
  }

  private List<Pair<String, Integer>> getAllTimeMovieCountPerGenre() {
    return movieQueryRepository.getMovieCountPerGenreBetween(ALL_TIME_RANGE);
  }

  private List<Pair<String, Integer>> getAllTimeMovieCountPerOriginalLanguage() {
    return movieQueryRepository.getMovieCountPerOriginalLanguageBetween(ALL_TIME_RANGE);
  }

  private AverageRatingPerYear getAllTimeRatedMoviesAveragePerYear() {
    return new AverageRatingPerYear(
        movieQueryRepository.getAverageMovieRatingPerYearBetween(ALL_TIME_RANGE));
  }

  private List<Pair<Decade, List<StatisticsMovie>>> getHighestRatedMoviesPerDecade() {
    return IntStream.rangeClosed(START_DECADE, END_DECADE)
        .filter(year -> year % 10 == 0)
        .mapToObj(Year::of)
        .map(year -> this.getHighestRatedMoviesForDecade(new Decade(year), new Limit(5)))
        .filter(pair -> !pair.value().isEmpty())
        .toList();
  }

  private Pair<Decade, List<StatisticsMovie>> getHighestRatedMoviesForDecade(
      Decade decade, Limit limit) {
    var dateRange = new DateRange(decade.yearDay(), decade.next().yearDay());
    List<StatisticsMovie> movies =
        movieQueryRepository.findHighestRatedMoviesReleasedBetween(dateRange, limit);

    return new Pair<>(decade, movies);
  }
}
