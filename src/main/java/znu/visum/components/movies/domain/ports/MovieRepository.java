package znu.visum.components.movies.domain.ports;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.time.LocalDate;
import java.util.Optional;

public interface MovieRepository {
  VisumPage<Movie> findPage(PageSearch<Movie> page);

  Optional<Movie> findById(long id);

  Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);

  Movie save(Movie movie);

  void deleteById(long id);
}
