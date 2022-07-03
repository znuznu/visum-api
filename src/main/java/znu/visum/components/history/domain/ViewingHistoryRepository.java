package znu.visum.components.history.domain;

import java.util.List;
import java.util.Optional;

public interface ViewingHistoryRepository {
  Optional<ViewingHistory> findById(long id);

  boolean existsById(long id);

  void deleteById(long id);

  ViewingHistory save(ViewingHistory history);

  List<ViewingHistory> findByMovieId(long id);
}
