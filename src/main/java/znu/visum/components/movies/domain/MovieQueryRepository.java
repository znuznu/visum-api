package znu.visum.components.movies.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.movies.usecases.getpage.domain.PageMovie;
import znu.visum.components.statistics.domain.AverageRating;
import znu.visum.components.statistics.domain.DateRange;
import znu.visum.components.statistics.domain.StatisticsMovie;
import znu.visum.core.models.common.Limit;
import znu.visum.core.models.common.Pair;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MovieQueryRepository {
  // Spring Sort is not supposed to be here but who cares
  VisumPage<PageMovie> findPage(int limit, int offset, Sort sort, String search);

  Optional<Movie> findById(long id);

  boolean existsById(long id);

  boolean existsByTmdbId(long id);

  long count();

  List<Movie> findAll();

  long countByReleaseYear(Year year);

  List<StatisticsMovie> findHighestRatedMoviesReleasedBetween(DateRange range, Limit limit);

  int getTotalRunningHoursBetween(DateRange range);

  List<Pair<String, Integer>> getMovieCountPerOriginalLanguageBetween(DateRange range);

  List<Pair<String, Integer>> getMovieCountPerGenreBetween(DateRange range);

  List<Pair<Year, Integer>> getMovieCountPerYearBetween(DateRange range);

  List<Pair<Year, AverageRating>> getAverageMovieRatingPerYearBetween(DateRange range);

  List<StatisticsMovie> findHighestRatedDuringYearOlderMovies(Year year);

  List<MovieDiaryFragment> findByDiaryFilters(DiaryFilters filters);
}
