package znu.visum.components.movies.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class DeleteByIdMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public DeleteByIdMovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void deleteById(long id) {
    if (movieRepository.findById(id).isEmpty()) {
      throw new NoSuchMovieIdException(Long.toString(id));
    }

    movieRepository.deleteById(id);
  }
}
