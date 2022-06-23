package znu.visum.components.history.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.errors.NoSuchViewingHistoryException;
import znu.visum.components.history.domain.port.MovieViewingHistoryRepository;

@Service
public class DeleteByIdMovieViewingHistoryService {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public DeleteByIdMovieViewingHistoryService(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  public void deleteById(long id) {
    if (movieViewingHistoryRepository.findById(id).isEmpty()) {
      throw new NoSuchViewingHistoryException(Long.toString(id));
    }

    movieViewingHistoryRepository.deleteById(id);
  }
}
