package znu.visum.components.people.actors.usecases.getpage.domain;

import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

public interface GetPageActorService {
  VisumPage<Actor> findPage(PageSearch<Actor> page);
}
