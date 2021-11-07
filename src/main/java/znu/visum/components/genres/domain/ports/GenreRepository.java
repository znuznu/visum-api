package znu.visum.components.genres.domain.ports;

import znu.visum.components.genres.domain.models.Genre;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

public interface GenreRepository {
  VisumPage<Genre> findPage(PageSearch<Genre> page);

  Optional<Genre> findById(long id);

  Optional<Genre> findByType(String type);

  void deleteById(long id);

  Genre save(Genre genre);
}
