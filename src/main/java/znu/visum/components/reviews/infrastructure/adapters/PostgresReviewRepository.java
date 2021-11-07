package znu.visum.components.reviews.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.components.reviews.infrastructure.models.MovieReviewEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class PostgresReviewRepository implements ReviewRepository {
  private final DataJpaMovieReviewRepository dataJpaMovieReviewRepository;

  @Autowired
  public PostgresReviewRepository(DataJpaMovieReviewRepository dataJpaMovieReviewRepository) {
    this.dataJpaMovieReviewRepository = dataJpaMovieReviewRepository;
  }

  @Override
  public VisumPage<Review> findPage(PageSearch<Review> page) {
    return SpringPageMapper.toVisumPage(
        dataJpaMovieReviewRepository.findPage(new PageSearch<>(page)), MovieReviewEntity::toDomain);
  }

  @Override
  public Optional<Review> findById(long id) {
    return dataJpaMovieReviewRepository.findById(id).map(MovieReviewEntity::toDomain);
  }

  @Override
  public void deleteById(long id) {
    this.dataJpaMovieReviewRepository.deleteById(id);
  }

  @Override
  public Review save(Review review) {
    return this.dataJpaMovieReviewRepository.save(MovieReviewEntity.from(review)).toDomain();
  }
}
