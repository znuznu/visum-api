package znu.visum.components.movies.usecases.removetowatch.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class RemoveToWatchMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public RemoveToWatchMovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public boolean removeToWatch(long movieId) {
    Movie movie =
        movieRepository
            .findById(movieId)
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

    if (movie.isToWatch()) {
      movie.setToWatch(false);
      movieRepository.save(movie);

      return true;
    }

    return false;
  }
}
