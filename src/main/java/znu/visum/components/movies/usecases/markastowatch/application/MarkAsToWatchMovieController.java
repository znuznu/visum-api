package znu.visum.components.movies.usecases.markastowatch.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.markastowatch.domain.MarkAsToWatchMovie;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkAsToWatchMovieController {
  private final MarkAsToWatchMovie markAsToWatchMovie;

  @Autowired
  public MarkAsToWatchMovieController(MarkAsToWatchMovie markAsToWatchMovie) {
    this.markAsToWatchMovie = markAsToWatchMovie;
  }

  @Operation(summary = "Mark the movie as a one to watch.")
  @PutMapping("/{id}/watchlist")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void markAsToWatch(@PathVariable long id) {
    markAsToWatchMovie.process(id);
  }
}
