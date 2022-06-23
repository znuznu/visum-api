package znu.visum.components.movies.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class GetByIdMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetByIdMovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public Movie findById(long id) {
    return movieRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(id)));
  }
}
