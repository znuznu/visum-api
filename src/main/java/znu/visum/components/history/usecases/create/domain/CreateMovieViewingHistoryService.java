package znu.visum.components.history.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.history.domain.port.MovieViewingHistoryRepository;
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.movies.domain.ports.MovieRepository;

@Service
public class CreateMovieViewingHistoryService {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  private final MovieRepository movieRepository;

  @Autowired
  public CreateMovieViewingHistoryService(
      MovieViewingHistoryRepository movieViewingHistoryRepository,
      MovieRepository movieRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
    this.movieRepository = movieRepository;
  }

  public MovieViewingHistory save(MovieViewingHistory movieViewingHistory) {
    if (movieRepository.findById(movieViewingHistory.getMovieId()).isEmpty()) {
      throw new NoSuchMovieIdException(Long.toString(movieViewingHistory.getMovieId()));
    }

    return movieViewingHistoryRepository.save(movieViewingHistory);
  }
}
