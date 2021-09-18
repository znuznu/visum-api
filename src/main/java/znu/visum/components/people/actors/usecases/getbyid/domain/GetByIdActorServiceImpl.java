package znu.visum.components.people.actors.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;

@Service
public class GetByIdActorServiceImpl implements GetByIdActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public GetByIdActorServiceImpl(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Override
  public Actor findById(long id) {
    return actorRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchActorIdException(Long.toString(id)));
  }
}
