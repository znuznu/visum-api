package znu.visum.business.history.movies.persistence.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.history.movies.models.MovieViewingHistory;
import znu.visum.business.history.movies.persistence.MovieViewingHistoryDao;

@Repository
public interface DataJpaMovieViewingHistoryDao extends
        MovieViewingHistoryDao,
        JpaRepository<MovieViewingHistory, Long>,
        JpaSpecificationExecutor<MovieViewingHistory> {
}
