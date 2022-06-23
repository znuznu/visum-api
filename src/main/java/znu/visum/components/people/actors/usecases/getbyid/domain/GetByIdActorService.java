package znu.visum.components.people.actors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;

@Service
public class GetByIdActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public GetByIdActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor findById(long id) {
    return actorRepository
        .findById(id)
        .orElseThrow(() -> NoSuchActorIdException.withId(Long.toString(id)));
  }
}
