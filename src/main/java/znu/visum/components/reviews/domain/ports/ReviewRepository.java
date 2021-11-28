package znu.visum.components.reviews.domain.ports;

import znu.visum.components.reviews.domain.models.Review;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

import java.time.Year;
import java.util.Optional;

public interface ReviewRepository {
  VisumPage<Review> findPage(PageSearch<Review> page);

  Optional<Review> findById(long id);

  void deleteById(long id);

  Review save(Review review);

  long count();

  long countAllByUpdateDateYear(Year year);
}
