package znu.visum.components.reviews.domain;

import org.springframework.data.domain.Sort;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.Year;
import java.util.Optional;

public interface ReviewRepository {
  VisumPage<Review> findPage(int limit, int offset, Sort sort, String search);

  Optional<Review> findById(long id);

  boolean existsById(long id);

  void deleteById(long id);

  Review save(Review review);

  long count();

  long countUpdated(Year year);
}
