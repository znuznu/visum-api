package znu.visum.components.history.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class CreateMovieViewingHistory {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  private final MovieRepository movieRepository;

  @Autowired
  public CreateMovieViewingHistory(
      MovieViewingHistoryRepository movieViewingHistoryRepository,
      MovieRepository movieRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
    this.movieRepository = movieRepository;
  }

  public MovieViewingHistory process(MovieViewingHistory movieViewingHistory) {
    if (movieRepository.findById(movieViewingHistory.getMovieId()).isEmpty()) {
      throw new NoSuchMovieIdException(Long.toString(movieViewingHistory.getMovieId()));
    }

    return movieViewingHistoryRepository.save(movieViewingHistory);
  }
}
