package znu.visum.components.movies.usecases.markastowatch.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;

@Service
public class MarkAsToWatchMovieServiceImpl implements MarkAsToWatchMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public MarkAsToWatchMovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public boolean markAsToWatch(long movieId) {
    Movie movie =
        movieRepository
            .findById(movieId)
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

    if (!movie.isToWatch()) {
      movie.setToWatch(true);
      movieRepository.save(movie);

      return true;
    }

    return false;
  }
}
