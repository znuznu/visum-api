package znu.visum.components.history.usecases.deprecated.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.history.domain.port.MovieViewingHistoryRepository;

import java.util.Optional;

@Service
public class GetByIdMovieViewingHistoryServiceImpl implements GetByIdMovieViewingHistoryService {
  private final MovieViewingHistoryRepository movieViewingHistoryRepository;

  @Autowired
  public GetByIdMovieViewingHistoryServiceImpl(
      MovieViewingHistoryRepository movieViewingHistoryRepository) {
    this.movieViewingHistoryRepository = movieViewingHistoryRepository;
  }

  @Override
  public Optional<MovieViewingHistory> findById(long id) {
    return movieViewingHistoryRepository.findById(id);
  }
}
