package znu.visum.components.people.actors.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.actors.domain.ports.ActorRepository;

@Service
public class DeleteByIdActorServiceImpl implements DeleteByIdActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public DeleteByIdActorServiceImpl(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Override
  public void deleteById(long id) {
    if (actorRepository.findById(id).isEmpty()) {
      throw NoSuchActorIdException.withId(Long.toString(id));
    }

    actorRepository.deleteById(id);
  }
}
