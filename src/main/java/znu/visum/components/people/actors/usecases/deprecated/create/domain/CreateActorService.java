package znu.visum.components.people.actors.usecases.deprecated.create.domain;

import znu.visum.components.people.actors.domain.Actor;

public interface CreateActorService {
  Actor save(Actor actorEntity);
}
