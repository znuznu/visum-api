package znu.visum.components.movies.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class DeleteByIdMovie {

  private final MovieRepository movieRepository;

  @Autowired
  public DeleteByIdMovie(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void process(long id) {
    if (!movieRepository.existsById(id)) {
      throw new NoSuchMovieIdException(Long.toString(id));
    }

    movieRepository.deleteById(id);
  }
}
