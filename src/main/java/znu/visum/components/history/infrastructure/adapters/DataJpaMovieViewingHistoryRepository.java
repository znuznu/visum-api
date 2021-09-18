package znu.visum.components.history.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.components.history.infrastructure.models.MovieViewingHistoryEntity;

import java.util.List;

@Repository
public interface DataJpaMovieViewingHistoryRepository
    extends JpaRepository<MovieViewingHistoryEntity, Long>,
        JpaSpecificationExecutor<MovieViewingHistoryEntity> {
  List<MovieViewingHistoryEntity> findByMovieId(long id);
}
