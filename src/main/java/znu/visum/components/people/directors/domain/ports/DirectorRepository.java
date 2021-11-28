package znu.visum.components.people.directors.domain.ports;

import org.springframework.data.domain.Sort;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface DirectorRepository {
  VisumPage<Director> findPage(int limit, int offset, Sort sort, String search);

  Optional<Director> findById(long id);

  Optional<Director> findByNameAndForename(String name, String forename);

  void deleteById(long id);

  Director save(Director movie);
}
