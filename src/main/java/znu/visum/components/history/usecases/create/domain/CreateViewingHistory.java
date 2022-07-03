package znu.visum.components.history.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.history.domain.ViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class CreateViewingHistory {
  private final ViewingHistoryRepository viewingHistoryRepository;

  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public CreateViewingHistory(
      ViewingHistoryRepository viewingHistoryRepository,
      MovieQueryRepository movieQueryRepository) {
    this.viewingHistoryRepository = viewingHistoryRepository;
    this.movieQueryRepository = movieQueryRepository;
  }

  public ViewingHistory process(ViewingHistory movieViewingHistory) {
    if (movieQueryRepository.findById(movieViewingHistory.getMovieId()).isEmpty()) {
      throw new NoSuchMovieIdException(Long.toString(movieViewingHistory.getMovieId()));
    }

    return viewingHistoryRepository.save(movieViewingHistory);
  }
}
