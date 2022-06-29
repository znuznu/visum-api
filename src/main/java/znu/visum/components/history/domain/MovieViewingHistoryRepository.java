package znu.visum.components.history.domain;

import java.util.List;
import java.util.Optional;

public interface MovieViewingHistoryRepository {
  Optional<MovieViewingHistory> findById(long id);

  boolean existsById(long id);

  void deleteById(long id);

  MovieViewingHistory save(MovieViewingHistory history);

  List<MovieViewingHistory> findByMovieId(long id);
}
