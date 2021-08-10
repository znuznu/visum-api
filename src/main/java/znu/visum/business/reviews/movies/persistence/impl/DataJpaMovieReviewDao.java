package znu.visum.business.reviews.movies.persistence.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.business.reviews.movies.persistence.MovieReviewDao;
import znu.visum.core.pagination.PageSearch;

@Repository
public interface DataJpaMovieReviewDao extends
        MovieReviewDao,
        JpaRepository<MovieReview, Long>,
        JpaSpecificationExecutor<MovieReview> {
    default Page<MovieReview> findPage(PageSearch<MovieReview> page) {
        return findAll(page.getSearch(), page);
    }
}
