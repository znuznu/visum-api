package znu.visum.components.movies.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class GetMovieById {
  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public GetMovieById(MovieQueryRepository movieQueryRepository) {
    this.movieQueryRepository = movieQueryRepository;
  }

  public Movie process(long id) {
    return movieQueryRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(id)));
  }
}
