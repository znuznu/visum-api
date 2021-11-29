package znu.visum.components.people.actors.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.components.people.actors.infrastructure.models.ActorEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SearchSpecification;
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
  public VisumPage<Actor> findPage(int limit, int offset, Sort sort, String search) {
    Specification<ActorEntity> searchSpecification = SearchSpecification.parse(search);

    PageSearch<ActorEntity> pageSearch =
        new PageSearch.Builder<ActorEntity>()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaActorRepository.findPage(pageSearch), ActorEntity::toDomain);
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
