package znu.visum.components.people.actors.domain;

import org.springframework.data.domain.Sort;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface ActorRepository {
  VisumPage<Actor> findPage(int limit, int offset, Sort sort, String search);

  Optional<Actor> findById(long id);

  Optional<Actor> findByTmdbId(long tmdbId);

  Actor save(Actor actor);

  void deleteById(long id);
}
