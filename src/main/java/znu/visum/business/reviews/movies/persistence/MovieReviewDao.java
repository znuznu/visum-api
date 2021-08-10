package znu.visum.business.reviews.movies.persistence;

import org.springframework.data.domain.Page;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface MovieReviewDao {
    Page<MovieReview> findPage(PageSearch<MovieReview> page);

    Optional<MovieReview> findById(long id);

    List<MovieReview> findAll();

    void deleteById(long id);

    MovieReview save(MovieReview review);
}
