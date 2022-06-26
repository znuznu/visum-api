package znu.visum.components.person.actors.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;

@Service
public class DeleteByIdActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public DeleteByIdActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public void deleteById(long id) {
    if (actorRepository.findById(id).isEmpty()) {
      throw NoSuchActorIdException.withId(Long.toString(id));
    }

    actorRepository.deleteById(id);
  }
}
