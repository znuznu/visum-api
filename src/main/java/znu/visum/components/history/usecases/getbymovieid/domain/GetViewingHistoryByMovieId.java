package znu.visum.components.history.usecases.getbymovieid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.history.domain.ViewingHistoryRepository;

import java.util.List;

@Service
public class GetViewingHistoryByMovieId {

  private final ViewingHistoryRepository viewingHistoryRepository;

  @Autowired
  public GetViewingHistoryByMovieId(ViewingHistoryRepository viewingHistoryRepository) {
    this.viewingHistoryRepository = viewingHistoryRepository;
  }

  public List<ViewingHistory> process(long id) {
    return viewingHistoryRepository.findByMovieId(id);
  }
}
