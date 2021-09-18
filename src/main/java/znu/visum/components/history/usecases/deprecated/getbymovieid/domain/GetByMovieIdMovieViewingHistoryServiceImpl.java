package znu.visum.components.history.usecases.deprecated.getbymovieid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.history.domain.port.MovieViewingHistoryRepository;

import java.util.List;

@Service
public class GetByMovieIdMovieViewingHistoryServiceImpl
    implements GetByMovieIdMovieViewingHistoryService {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public GetByMovieIdMovieViewingHistoryServiceImpl(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  @Override
  public List<MovieViewingHistory> findByMovieId(long id) {
    return movieViewingHistoryRepository.findByMovieId(id);
  }
}
