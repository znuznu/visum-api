package znu.visum.components.people.directors.domain.ports;

import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

public interface DirectorRepository {
  VisumPage<Director> findPage(PageSearch<Director> page);

  Optional<Director> findById(long id);

  Optional<Director> findByNameAndForename(String name, String forename);

  void deleteById(long id);

  Director save(Director movie);
}
