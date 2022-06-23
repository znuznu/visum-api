package znu.visum.components.movies.usecases.markasfavorite.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class MarkAsFavoriteMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public MarkAsFavoriteMovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public boolean markAsFavorite(long movieId) {
    Movie movie =
        movieRepository
            .findById(movieId)
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

    if (!movie.isFavorite()) {
      movie.setFavorite(true);
      movieRepository.save(movie);

      return true;
    }

    return false;
  }
}
