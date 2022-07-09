package znu.visum.components.history.domain;

import java.util.List;
import java.util.Optional;

public interface ViewingHistoryRepository {
  Optional<ViewingEntry> findById(long id);

  boolean existsById(long id);

  void deleteById(long id);

  ViewingEntry save(ViewingEntry entry);

  List<ViewingEntry> findByMovieId(long id);
}
