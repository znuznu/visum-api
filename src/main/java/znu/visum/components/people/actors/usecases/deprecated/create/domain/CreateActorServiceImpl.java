package znu.visum.components.people.actors.usecases.deprecated.create.domain;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;

@Service
public class CreateActorServiceImpl implements CreateActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public CreateActorServiceImpl(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Override
  public Actor save(Actor actor) {
    if (actor.getId() != null) {
      throw new PersistentObjectException("Can't create Actor with fixed id");
    }

    return actorRepository.save(actor);
  }
}
