package znu.visum.components.movies.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieCommandRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class DeleteMovieById {

  private final MovieQueryRepository queryRepository;
  private final MovieCommandRepository commandRepository;

  @Autowired
  public DeleteMovieById(
      MovieQueryRepository queryRepository, MovieCommandRepository commandRepository) {
    this.queryRepository = queryRepository;
    this.commandRepository = commandRepository;
  }

  public void process(long id) {
    if (!queryRepository.existsById(id)) {
      throw new NoSuchMovieIdException(Long.toString(id));
    }

    commandRepository.deleteById(id);
  }
}
