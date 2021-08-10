package znu.visum.business.reviews.movies.services;

import org.springframework.data.domain.Page;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface MovieReviewService {
    Page<MovieReview> findPage(PageSearch<MovieReview> page);

    Optional<MovieReview> findById(long id);

    List<MovieReview> findAll();

    MovieReview create(MovieReview movieReview);

    MovieReview update(MovieReview movieReview);

    void deleteById(long id);
}
