package znu.visum.components.people.actors.infrastructure.adapters;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.components.people.actors.infrastructure.models.ActorEntity;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

@Repository
public interface DataJpaActorRepository
    extends JpaRepository<ActorEntity, Long>, JpaSpecificationExecutor<ActorEntity> {
  default Page<ActorEntity> findPage(PageSearch<ActorEntity> page) {
    return findAll(page.getSearch(), page);
  }

  Optional<ActorEntity> findByNameAndForename(String name, String forename);
}
