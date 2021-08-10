package znu.visum.business.people.actor.services.impl;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.business.people.actor.persistence.ActorDao;
import znu.visum.business.people.actor.services.ActorService;
import znu.visum.core.pagination.PageSearch;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @Override
    public Page<Actor> findPage(PageSearch<Actor> pageSearch) {
        return actorDao.findPage(pageSearch);
    }

    @Override
    public Actor create(Actor actor) {
        if (actor.getId() != null) {
            throw new PersistentObjectException("Can't create Actor with fixed id");
        }

        return actorDao.save(actor);
    }

    @Override
    public Optional<Actor> findById(long id) {
        return actorDao.findById(id);
    }

    @Override
    public Actor update(Actor actor) {
        actorDao.findById(actor.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("No Actor with id %d", actor.getId())));
        return actorDao.save(actor);
    }

    @Override
    public void deleteById(long id) {
        actorDao.deleteById(id);
    }
}
