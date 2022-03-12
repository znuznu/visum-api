package znu.visum.components.statistics.domain.services;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public interface StatisticsService {
  List<Movie> findHighestRatedMoviesReleasedBetween(LocalDate start, LocalDate end, int limit);

  int getTotalRunningHoursBetween(LocalDate start, LocalDate end);

  int getTotalRunningHoursOfTheYear(Year year);

  List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesOfTheYearPerGenre(Year year);

  List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(LocalDate start, LocalDate end);

  List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(LocalDate start, LocalDate end);

  long getReviewCount();

  Pair<Integer, List<Movie>> getHighestRatedMoviesForDecade(int decade, int limit);

  long getReviewCountOfTheYear(Year year);

  long getMovieCountOfTheYear(Year year);

  List<Movie> findHighestRatedMoviesOfTheYear(Year year, int limit);

  List<Movie> findHighestRatedDuringYearOlderMovies(Year year);
}
