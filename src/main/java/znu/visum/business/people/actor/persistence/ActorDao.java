package znu.visum.business.people.actor.persistence;

import org.springframework.data.domain.Page;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface ActorDao {
    Page<Actor> findPage(PageSearch<Actor> page);

    Optional<Actor> findById(long id);

    Optional<Actor> findByNameAndForename(String name, String forename);

    List<Actor> findAll();

    void deleteById(long id);

    Actor save(Actor actor);
}
