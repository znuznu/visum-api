package znu.visum.components.movies.usecases.removefavorite.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

@Service
public class RemoveFavoriteMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public RemoveFavoriteMovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public boolean removeFavorite(long movieId) {
    Movie movie =
        movieRepository
            .findById(movieId)
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

    if (movie.isFavorite()) {
      movie.setFavorite(false);
      movieRepository.save(movie);

      return true;
    }

    return false;
  }
}
