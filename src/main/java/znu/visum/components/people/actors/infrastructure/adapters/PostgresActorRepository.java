package znu.visum.components.people.actors.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.components.people.actors.infrastructure.models.ActorEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class PostgresActorRepository implements ActorRepository {
  private final DataJpaActorRepository dataJpaActorRepository;

  @Autowired
  public PostgresActorRepository(DataJpaActorRepository dataJpaActorRepository) {
    this.dataJpaActorRepository = dataJpaActorRepository;
  }

  @Override
  public VisumPage<Actor> findPage(PageSearch<Actor> page) {
    return SpringPageMapper.toVisumPage(
        dataJpaActorRepository.findPage(new PageSearch<>(page)), ActorEntity::toDomain);
  }

  @Override
  public Optional<Actor> findById(long id) {
    return dataJpaActorRepository.findById(id).map(ActorEntity::toDomain);
  }

  @Override
  public Optional<Actor> findByNameAndForename(String name, String forename) {
    return dataJpaActorRepository.findByNameAndForename(name, forename).map(ActorEntity::toDomain);
  }

  @Override
  public Actor save(Actor actor) {
    return dataJpaActorRepository.save(ActorEntity.from(actor)).toDomain();
  }

  @Override
  public void deleteById(long id) {
    dataJpaActorRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    dataJpaActorRepository.deleteAll();
  }
}
