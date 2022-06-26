package znu.visum.components.person.actors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;

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
