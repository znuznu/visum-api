package znu.visum.components.movies.usecases.markasfavorite.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.markasfavorite.domain.MarkAsFavoriteMovie;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkAsFavoriteMovieController {
  private final MarkAsFavoriteMovie markAsFavoriteMovie;

  @Autowired
  public MarkAsFavoriteMovieController(MarkAsFavoriteMovie markAsFavoriteMovie) {
    this.markAsFavoriteMovie = markAsFavoriteMovie;
  }

  @Operation(summary = "Mark the movie as a favorite one.")
  @PutMapping("/{id}/favorite")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void markAsFavorite(@PathVariable long id) {
    markAsFavoriteMovie.process(id);
  }
}
