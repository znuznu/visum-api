package znu.visum.components.statistics.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.reviews.domain.ReviewRepository;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class StatisticsService {
  private final MovieQueryRepository movieQueryRepository;

  private final ReviewRepository reviewRepository;

  @Autowired
  public StatisticsService(
      MovieQueryRepository movieQueryRepository, ReviewRepository reviewRepository) {
    this.movieQueryRepository = movieQueryRepository;
    this.reviewRepository = reviewRepository;
  }

  public List<Movie> findHighestRatedMoviesReleasedBetween(
      LocalDate start, LocalDate end, int limit) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    if (limit < 0) {
      throw new IllegalArgumentException("Negative limit is not allowed.");
    }

    return this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(start, end, limit);
  }

  public int getTotalRunningHoursBetween(LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieQueryRepository.getTotalRunningHoursBetween(start, end);
  }

  public int getTotalRunningHoursOfTheYear(Year year) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.getTotalRunningHoursBetween(startDate, endDate);
  }

  public List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieQueryRepository.getNumberOfMoviesPerOriginalLanguageBetween(start, end);
  }

  public List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieQueryRepository.getNumberOfMoviesPerGenreBetween(start, end);
  }

  public List<Pair<String, Integer>> getNumberOfMoviesOfTheYearPerGenre(Year year) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.getNumberOfMoviesPerGenreBetween(startDate, endDate);
  }

  public List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieQueryRepository.getNumberOfMoviesPerYearBetween(start, end);
  }

  public List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieQueryRepository.getRatedMoviesAveragePerYearBetween(start, end);
  }

  public long getReviewCount() {
    return this.reviewRepository.count();
  }

  public Pair<Integer, List<Movie>> getHighestRatedMoviesForDecade(int decade, int limit) {
    if (decade % 10 != 0) {
      throw new InvalidDecadeException(decade);
    }

    if (limit < 0) {
      throw new IllegalArgumentException("Negative limit is not allowed.");
    }

    List<Movie> movies =
        this.movieQueryRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.ofYearDay(decade, 1), LocalDate.ofYearDay(decade + 10, 1), limit);

    return new Pair<>(decade, movies);
  }

  public long getReviewCountOfTheYear(Year year) {
    return this.reviewRepository.countAllByUpdateDateYear(year);
  }

  public long getMovieCountOfTheYear(Year year) {
    return this.movieQueryRepository.countAllByReleaseDateYear(year);
  }

  public List<Movie> findHighestRatedMoviesOfTheYear(Year year, int limit) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.findHighestRatedMoviesReleasedBetween(startDate, endDate, limit);
  }

  public List<Movie> findHighestRatedDuringYearOlderMovies(Year year) {
    return this.movieQueryRepository.findHighestRatedDuringYearOlderMovies(year);
  }
}
