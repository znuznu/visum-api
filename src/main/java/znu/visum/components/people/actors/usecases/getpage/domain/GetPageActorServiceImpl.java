package znu.visum.components.people.actors.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Service
public class GetPageActorServiceImpl implements GetPageActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public GetPageActorServiceImpl(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Override
  public VisumPage<Actor> findPage(PageSearch<Actor> pageSearch) {
    return actorRepository.findPage(pageSearch);
  }
}
