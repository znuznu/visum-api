package znu.visum.components.reviews.infrastructure.adapters;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.components.reviews.infrastructure.models.MovieReviewEntity;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Repository
public interface DataJpaMovieReviewRepository
    extends JpaRepository<MovieReviewEntity, Long>, JpaSpecificationExecutor<MovieReviewEntity> {
  default Page<MovieReviewEntity> findPage(PageSearch<MovieReviewEntity> page) {
    return findAll(page.getSearch(), page);
  }
}
