package znu.visum.components.genres.infrastructure.adapters;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.components.genres.infrastructure.models.GenreEntity;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.util.Optional;

@Repository
public interface DataJpaGenreRepository
    extends JpaRepository<GenreEntity, Long>, JpaSpecificationExecutor<GenreEntity> {
  Optional<GenreEntity> findByType(String type);

  default Page<GenreEntity> findPage(PageSearch<GenreEntity> page) {
    return findAll(page.getSearch(), page);
  }
}
