package znu.visum.business.people.actor.services;

import org.springframework.data.domain.Page;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.core.pagination.PageSearch;

import java.util.Optional;

public interface ActorService {
    Page<Actor> findPage(PageSearch<Actor> page);

    Actor create(Actor actor);

    Optional<Actor> findById(long id);

    Actor update(Actor actor);

    void deleteById(long id);
}
