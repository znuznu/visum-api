package znu.visum.components.people.actors.usecases.getbyid.domain;

import znu.visum.components.people.actors.domain.models.Actor;

public interface GetByIdActorService {
  Actor findById(long id);
}
