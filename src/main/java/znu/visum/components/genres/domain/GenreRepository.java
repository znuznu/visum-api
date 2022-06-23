package znu.visum.components.genres.domain;

import org.springframework.data.domain.Sort;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface GenreRepository {
  VisumPage<Genre> findPage(int limit, int offset, Sort sort, String search);

  Optional<Genre> findById(long id);

  Optional<Genre> findByType(String type);

  void deleteById(long id);

  Genre save(Genre genre);
}
