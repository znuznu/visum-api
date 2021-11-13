package znu.visum.components.statistics.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.components.statistics.domain.errors.InvalidDecadeException;
import znu.visum.components.statistics.domain.errors.StatisticsDateRangeException;
import znu.visum.core.models.domain.Pair;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
  private final MovieRepository movieRepository;

  private final ReviewRepository reviewRepository;

  @Autowired
  public StatisticsServiceImpl(MovieRepository movieRepository, ReviewRepository reviewRepository) {
    this.movieRepository = movieRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public List<Movie> findHighestRatedMoviesReleasedBetween(
      LocalDate start, LocalDate end, int limit) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    if (limit < 0) {
      throw new IllegalArgumentException("Negative limit is not allowed.");
    }

    return this.movieRepository.findHighestRatedMoviesReleasedBetween(start, end, limit);
  }

  @Override
  public int getTotalRunningHoursBetween(LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieRepository.getTotalRunningHoursBetween(start, end);
  }

  @Override
  public int getTotalRunningHoursOfTheYear(Year year) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.getTotalRunningHoursBetween(startDate, endDate);
  }

  @Override
  public List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieRepository.getNumberOfMoviesPerOriginalLanguageBetween(start, end);
  }

  @Override
  public List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieRepository.getNumberOfMoviesPerGenreBetween(start, end);
  }

  @Override
  public List<Pair<String, Integer>> getNumberOfMoviesOfTheYearPerGenre(Year year) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.getNumberOfMoviesPerGenreBetween(startDate, endDate);
  }

  @Override
  public List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieRepository.getNumberOfMoviesPerYearBetween(start, end);
  }

  @Override
  public List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(
      LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new StatisticsDateRangeException();
    }

    return this.movieRepository.getRatedMoviesAveragePerYearBetween(start, end);
  }

  @Override
  public long getReviewCount() {
    return this.reviewRepository.count();
  }

  @Override
  public Pair<Integer, List<Movie>> getHighestRatedMoviesForDecade(int decade, int limit) {
    if (decade % 10 != 0) {
      throw new InvalidDecadeException(decade);
    }

    if (limit < 0) {
      throw new IllegalArgumentException("Negative limit is not allowed.");
    }

    List<Movie> movies =
        this.movieRepository.findHighestRatedMoviesReleasedBetween(
            LocalDate.ofYearDay(decade, 1), LocalDate.ofYearDay(decade + 10, 1), limit);

    return new Pair<>(decade, movies);
  }

  @Override
  public long getReviewCountOfTheYear(Year year) {
    return this.reviewRepository.countAllByUpdateDateYear(year);
  }

  @Override
  public long getMovieCountOfTheYear(Year year) {
    return this.movieRepository.countAllByReleaseDateYear(year);
  }

  @Override
  public List<Movie> findHighestRatedMoviesOfTheYear(Year year, int limit) {
    LocalDate startDate = LocalDate.of(year.getValue(), 1, 1);
    LocalDate endDate = LocalDate.of(year.getValue() + 1, 1, 1);

    return this.findHighestRatedMoviesReleasedBetween(startDate, endDate, limit);
  }

  @Override
  public List<Movie> findHighestRatedDuringYearOlderMovies(Year year) {
    return this.movieRepository.findHighestRatedDuringYearOlderMovies(year);
  }
}
