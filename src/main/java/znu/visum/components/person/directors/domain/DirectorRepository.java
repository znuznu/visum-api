package znu.visum.components.person.directors.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface DirectorRepository {
  VisumPage<Director> findPage(int limit, int offset, Sort sort, String search);

  Optional<Director> findById(long id);

  Optional<Director> findByTmdbId(long tmdbId);

  void deleteById(long id);

  Director save(Director director);

  DirectorFromMovie save(DirectorFromMovie director);
}
