package znu.visum.components.history.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataJpaMovieViewingHistoryRepository
    extends JpaRepository<MovieViewingHistoryEntity, Long>,
        JpaSpecificationExecutor<MovieViewingHistoryEntity> {
  List<MovieViewingHistoryEntity> findByMovieId(long id);
}
