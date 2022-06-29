package znu.visum.components.history.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;
import znu.visum.components.history.domain.NoSuchViewingHistoryException;

@Service
public class DeleteByIdMovieViewingHistory {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public DeleteByIdMovieViewingHistory(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  public void process(long id) {
    if (!movieViewingHistoryRepository.existsById(id)) {
      throw new NoSuchViewingHistoryException(Long.toString(id));
    }

    movieViewingHistoryRepository.deleteById(id);
  }
}
