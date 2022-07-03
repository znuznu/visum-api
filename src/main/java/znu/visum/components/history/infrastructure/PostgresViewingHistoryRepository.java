package znu.visum.components.history.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.history.domain.ViewingHistoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostgresViewingHistoryRepository implements ViewingHistoryRepository {
  private final DataJpaMovieViewingHistoryRepository dataJpaMovieViewingHistoryRepository;

  @Autowired
  public PostgresViewingHistoryRepository(
      DataJpaMovieViewingHistoryRepository dataJpaMovieViewingHistoryRepository) {
    this.dataJpaMovieViewingHistoryRepository = dataJpaMovieViewingHistoryRepository;
  }

  @Override
  public Optional<ViewingHistory> findById(long id) {
    return dataJpaMovieViewingHistoryRepository
        .findById(id)
        .map(MovieViewingHistoryEntity::toDomain);
  }

  @Override
  public boolean existsById(long id) {
    return dataJpaMovieViewingHistoryRepository.existsById(id);
  }

  @Override
  public void deleteById(long id) {
    this.dataJpaMovieViewingHistoryRepository.deleteById(id);
  }

  @Override
  public ViewingHistory save(ViewingHistory viewingHistory) {
    return this.dataJpaMovieViewingHistoryRepository
        .save(MovieViewingHistoryEntity.from(viewingHistory))
        .toDomain();
  }

  @Override
  public List<ViewingHistory> findByMovieId(long id) {
    return this.dataJpaMovieViewingHistoryRepository.findByMovieId(id).stream()
        .map(MovieViewingHistoryEntity::toDomain)
        .collect(Collectors.toList());
  }
}
