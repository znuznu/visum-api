package znu.visum.components.movies.domain.ports;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.models.domain.Pair;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
  VisumPage<Movie> findPage(PageSearch<Movie> page);

  Optional<Movie> findById(long id);

  Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);

  Movie save(Movie movie);

  void deleteById(long id);

  long count();

  long countAllByReleaseDateYear(Year year);

  List<Movie> findHighestRatedMoviesReleasedBetween(LocalDate start, LocalDate end, int limit);

  int getTotalRunningHoursBetween(LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(LocalDate start, LocalDate end);

  List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(LocalDate start, LocalDate end);

  List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(LocalDate start, LocalDate end);

  List<Movie> findHighestRatedDuringYearOlderMovies(Year year);
}
