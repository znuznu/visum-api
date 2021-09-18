package znu.visum.components.history.domain.port;

import znu.visum.components.history.domain.models.MovieViewingHistory;

import java.util.List;
import java.util.Optional;

public interface MovieViewingHistoryRepository {
  Optional<MovieViewingHistory> findById(long id);

  void deleteById(long id);

  MovieViewingHistory save(MovieViewingHistory history);

  List<MovieViewingHistory> findByMovieId(long id);
}
