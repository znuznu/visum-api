package znu.visum.components.people.actors.usecases.getpage.domain;

import org.springframework.data.domain.Sort;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.core.pagination.domain.VisumPage;

public interface GetPageActorService {
  VisumPage<Actor> findPage(int limit, int offset, Sort sort, String search);
}
