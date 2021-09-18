package znu.visum.components.people.actors.domain.ports;

import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

public interface ActorRepository {
  VisumPage<Actor> findPage(PageSearch<Actor> page);

  Optional<Actor> findById(long id);

  Optional<Actor> findByNameAndForename(String name, String forename);

  Actor save(Actor actor);

  void deleteById(long id);

  void deleteAll();
}
