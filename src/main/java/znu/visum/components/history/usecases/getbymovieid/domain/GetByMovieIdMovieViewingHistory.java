package znu.visum.components.history.usecases.getbymovieid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;

import java.util.List;

@Service
public class GetByMovieIdMovieViewingHistory {

  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public GetByMovieIdMovieViewingHistory(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  public List<MovieViewingHistory> process(long id) {
    return movieViewingHistoryRepository.findByMovieId(id);
  }
}
