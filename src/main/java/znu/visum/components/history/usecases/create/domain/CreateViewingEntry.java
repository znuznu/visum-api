package znu.visum.components.history.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.history.domain.ViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class CreateViewingEntry {
  private final ViewingHistoryRepository viewingHistoryRepository;

  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public CreateViewingEntry(
      ViewingHistoryRepository viewingHistoryRepository,
      MovieQueryRepository movieQueryRepository) {
    this.viewingHistoryRepository = viewingHistoryRepository;
    this.movieQueryRepository = movieQueryRepository;
  }

  public ViewingEntry process(ViewingEntry viewingEntry) {
    if (movieQueryRepository.findById(viewingEntry.getMovieId()).isEmpty()) {
      throw new NoSuchMovieIdException(Long.toString(viewingEntry.getMovieId()));
    }

    return viewingHistoryRepository.save(viewingEntry);
  }
}
