package znu.visum.components.movies.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;

@Service
public class GetByIdMovieServiceImpl implements GetByIdMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetByIdMovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public Movie findById(long id) {
    return movieRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(id)));
  }
}
