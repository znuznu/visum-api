package znu.visum.components.history.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.history.domain.port.MovieViewingHistoryRepository;
import znu.visum.components.history.infrastructure.models.MovieViewingHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostgresMovieViewingHistoryRepository implements MovieViewingHistoryRepository {
  private final DataJpaMovieViewingHistoryRepository dataJpaMovieViewingHistoryRepository;

  @Autowired
  public PostgresMovieViewingHistoryRepository(
      DataJpaMovieViewingHistoryRepository dataJpaMovieViewingHistoryRepository) {
    this.dataJpaMovieViewingHistoryRepository = dataJpaMovieViewingHistoryRepository;
  }

  @Override
  public Optional<MovieViewingHistory> findById(long id) {
    return dataJpaMovieViewingHistoryRepository
        .findById(id)
        .map(MovieViewingHistoryEntity::toDomain);
  }

  @Override
  public void deleteById(long id) {
    this.dataJpaMovieViewingHistoryRepository.deleteById(id);
  }

  @Override
  public MovieViewingHistory save(MovieViewingHistory movieViewingHistory) {
    return this.dataJpaMovieViewingHistoryRepository
        .save(MovieViewingHistoryEntity.from(movieViewingHistory))
        .toDomain();
  }

  @Override
  public List<MovieViewingHistory> findByMovieId(long id) {
    return this.dataJpaMovieViewingHistoryRepository.findByMovieId(id).stream()
        .map(MovieViewingHistoryEntity::toDomain)
        .collect(Collectors.toList());
  }
}
