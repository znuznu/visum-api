package znu.visum.components.history.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.history.domain.ViewingEntry;
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
  public Optional<ViewingEntry> findById(long id) {
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
  public ViewingEntry save(ViewingEntry viewingEntry) {
    return this.dataJpaMovieViewingHistoryRepository
        .save(MovieViewingHistoryEntity.from(viewingEntry))
        .toDomain();
  }

  @Override
  public List<ViewingEntry> findByMovieId(long id) {
    return this.dataJpaMovieViewingHistoryRepository.findByMovieId(id).stream()
        .map(MovieViewingHistoryEntity::toDomain)
        .collect(Collectors.toList());
  }
}
