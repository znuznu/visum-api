package znu.visum.components.person.actors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;

@Service
public class GetByIdActor {

  private final ActorRepository actorRepository;

  @Autowired
  public GetByIdActor(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor process(long id) {
    return actorRepository
        .findById(id)
        .orElseThrow(() -> NoSuchActorIdException.withId(Long.toString(id)));
  }
}
