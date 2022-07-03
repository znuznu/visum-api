package znu.visum.components.history.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.NoSuchViewingHistoryException;
import znu.visum.components.history.domain.ViewingHistoryRepository;

@Service
public class DeleteViewingHistoryById {
  private final ViewingHistoryRepository viewingHistoryRepository;

  @Autowired
  public DeleteViewingHistoryById(ViewingHistoryRepository viewingHistoryRepository) {
    this.viewingHistoryRepository = viewingHistoryRepository;
  }

  public void process(long id) {
    if (!viewingHistoryRepository.existsById(id)) {
      throw new NoSuchViewingHistoryException(Long.toString(id));
    }

    viewingHistoryRepository.deleteById(id);
  }
}
