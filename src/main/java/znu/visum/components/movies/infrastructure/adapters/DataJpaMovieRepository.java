package znu.visum.components.movies.infrastructure.adapters;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DataJpaMovieRepository
    extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {
  default Page<MovieEntity> findPage(PageSearch<MovieEntity> page) {
    return findAll(page.getSearch(), page);
  }

  Optional<MovieEntity> findByTitleAndReleaseDate(String title, LocalDate releaseDate);
}
