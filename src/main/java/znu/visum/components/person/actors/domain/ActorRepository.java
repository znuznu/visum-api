package znu.visum.components.person.actors.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.person.actors.usecases.getpage.domain.PageActor;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

public interface ActorRepository {
  VisumPage<PageActor> findPage(int limit, int offset, Sort sort, String search);

  Optional<Actor> findById(long id);

  boolean existsById(long id);

  Optional<Actor> findByTmdbId(long tmdbId);

  Actor save(Actor actor);

  void deleteById(long id);
}
