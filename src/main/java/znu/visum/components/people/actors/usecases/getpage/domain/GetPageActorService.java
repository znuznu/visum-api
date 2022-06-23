package znu.visum.components.people.actors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public GetPageActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public VisumPage<Actor> findPage(int limit, int offset, Sort sort, String search) {
    return actorRepository.findPage(limit, offset, sort, search);
  }
}
