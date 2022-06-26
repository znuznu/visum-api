package znu.visum.components.person.actors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorRepository;
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
