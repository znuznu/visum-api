package znu.visum.components.people.directors.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

@Repository
public interface DataJpaDirectorRepository
    extends JpaRepository<DirectorEntity, Long>, JpaSpecificationExecutor<DirectorEntity> {
  default Page<DirectorEntity> findPage(PageSearch<DirectorEntity> page) {
    return findAll(page.getSearch(), page);
  }

  Optional<DirectorEntity> findByMetadataEntity_TmdbId(long tmdbId);
}
