package znu.visum.components.people.actors.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.actors.domain.ActorRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
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
    Specification<ActorEntity> searchSpecification = PaginationSearchSpecification.parse(search);

    PageSearch<ActorEntity> pageSearch =
        PageSearch.<ActorEntity>builder()
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
  public Optional<Actor> findByTmdbId(long tmdbId) {
    return dataJpaActorRepository.findByMetadataEntity_TmdbId(tmdbId).map(ActorEntity::toDomain);
  }

  @Override
  public Actor save(Actor actor) {
    return dataJpaActorRepository.save(ActorEntity.from(actor)).toDomain();
  }

  @Override
  public ActorFromMovie save(ActorFromMovie actor) {
    return dataJpaActorRepository.save(ActorEntity.from(actor)).toActorFromMovieDomain();
  }

  @Override
  public void deleteById(long id) {
    dataJpaActorRepository.deleteById(id);
  }
}
