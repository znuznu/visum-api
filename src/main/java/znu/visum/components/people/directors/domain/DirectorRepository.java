package znu.visum.components.people.directors.domain;

import org.springframework.data.domain.Sort;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface DirectorRepository {
  VisumPage<Director> findPage(int limit, int offset, Sort sort, String search);

  Optional<Director> findById(long id);

  Optional<Director> findByTmdbId(long tmdbId);

  void deleteById(long id);

  Director save(Director movie);
}
