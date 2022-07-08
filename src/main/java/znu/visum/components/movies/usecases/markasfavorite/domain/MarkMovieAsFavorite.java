package znu.visum.components.movies.usecases.markasfavorite.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieCommandRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class MarkMovieAsFavorite {

  private final MovieQueryRepository queryRepository;
  private final MovieCommandRepository commandRepository;

  @Autowired
  public MarkMovieAsFavorite(
      MovieQueryRepository queryRepository, MovieCommandRepository commandRepository) {
    this.queryRepository = queryRepository;
    this.commandRepository = commandRepository;
  }

  public void process(long movieId) {
    boolean movieExists = queryRepository.existsById(movieId);
    if (!movieExists) {
      throw NoSuchMovieIdException.with(Long.toString(movieId));
    }

    commandRepository.updateFavorite(movieId, true);
  }
}
