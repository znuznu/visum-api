package znu.visum.components.movies.usecases.deprecated.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.usecases.deprecated.update.domain.UpdateMovieService;

import java.util.NoSuchElementException;

@Service
public class UpdateMovieServiceImpl implements UpdateMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public UpdateMovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public Movie update(Movie movie) {
    movieRepository
        .findById(movie.getId())
        .orElseThrow(
            () -> new NoSuchElementException(String.format("No Movie with id %d", movie.getId())));

    return movieRepository.save(movie);
  }
}
