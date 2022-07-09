package znu.visum.components.movies.domain;

import org.springframework.data.domain.Sort;
import znu.visum.core.models.common.Pair;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MovieQueryRepository {
  // TODO Spring Sort is not supposed to be here!
  // TODO Create a VisumSpecification inside the domain layer ?
  VisumPage<Movie> findPage(int limit, int offset, Sort sort, String search);

  Optional<Movie> findById(long id);

  boolean existsById(long id);

  boolean existsByTmdbId(long id);

  long count();

  List<Movie> findAll();

  long countAllByReleaseDateYear(Year year);

  List<Movie> findHighestRatedMoviesReleasedBetween(LocalDate start, LocalDate end, int limit);

  int getTotalRunningHoursBetween(LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end);

  List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(LocalDate start, LocalDate end);

  List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(LocalDate start, LocalDate end);

  List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(LocalDate start, LocalDate end);

  List<Movie> findHighestRatedDuringYearOlderMovies(Year year);

  List<MovieDiaryFragment> findByDiaryFilters(DiaryFilters filters);
}
