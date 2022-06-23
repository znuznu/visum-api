package znu.visum.components.history.usecases.getbymovieid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;

import java.util.List;

@Service
public class GetByMovieIdMovieViewingHistoryService {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public GetByMovieIdMovieViewingHistoryService(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  public List<MovieViewingHistory> findByMovieId(long id) {
    return movieViewingHistoryRepository.findByMovieId(id);
  }
}
