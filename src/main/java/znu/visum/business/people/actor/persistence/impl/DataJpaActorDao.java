package znu.visum.business.people.actor.persistence.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.business.people.actor.persistence.ActorDao;
import znu.visum.core.pagination.PageSearch;

@Repository
public interface DataJpaActorDao extends
        ActorDao,
        JpaRepository<Actor, Long>,
        JpaSpecificationExecutor<Actor> {

    default Page<Actor> findPage(PageSearch<Actor> page) {
        return findAll(page.getSearch(), page);
    }

    default long count(PageSearch<Actor> page) {
        return count(page.getSearch());
    }
}
