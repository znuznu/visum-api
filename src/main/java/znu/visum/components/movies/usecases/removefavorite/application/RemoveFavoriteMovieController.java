package znu.visum.components.movies.usecases.removefavorite.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.removefavorite.domain.RemoveFavoriteMovie;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemoveFavoriteMovieController {
  private final RemoveFavoriteMovie removeFavoriteMovie;

  @Autowired
  public RemoveFavoriteMovieController(RemoveFavoriteMovie removeFavoriteMovie) {
    this.removeFavoriteMovie = removeFavoriteMovie;
  }

  @Operation(summary = "Mark the movie as a favorite one.")
  @DeleteMapping("/{id}/favorite")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFavorite(@PathVariable long id) {
    removeFavoriteMovie.process(id);
  }
}
