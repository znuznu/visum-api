package znu.visum.components.reviews.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.genres.infrastructure.models.GenreEntity;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.components.reviews.infrastructure.models.MovieReviewEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
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
  public VisumPage<Review> findPage(int limit, int offset, Sort sort, String search) {
    Specification<MovieReviewEntity> searchSpecification =
        PaginationSearchSpecification.parse(search);

    PageSearch<MovieReviewEntity> pageSearch =
        PageSearch.<MovieReviewEntity>builder()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaMovieReviewRepository.findPage(pageSearch), MovieReviewEntity::toDomain);
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

  @Override
  public long count() {
    return this.dataJpaMovieReviewRepository.count();
  }

  @Override
  public long countAllByUpdateDateYear(Year year) {
    LocalDateTime startDate =
        LocalDateTime.of(LocalDate.ofYearDay(year.getValue(), 1), LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(LocalDate.of(year.getValue(), 12, 31), LocalTime.MIN);

    return this.dataJpaMovieReviewRepository.countAllByUpdateDateBetween(startDate, endDate);
  }
}
