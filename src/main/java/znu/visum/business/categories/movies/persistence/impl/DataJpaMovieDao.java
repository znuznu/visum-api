package znu.visum.business.categories.movies.persistence.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.categories.movies.persistence.MovieDao;
import znu.visum.business.people.director.models.Director;
import znu.visum.core.pagination.PageSearch;

import java.util.Optional;

@Repository
public interface DataJpaMovieDao extends
        MovieDao,
        JpaRepository<Movie, Long>,
        JpaSpecificationExecutor<Movie> {
    default Page<Movie> findPage(PageSearch<Movie> page) {
        return findAll(page.getSearch(), page);
    }
}
