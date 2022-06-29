package znu.visum.components.movies.usecases.removetowatch.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.removetowatch.domain.RemoveToWatchMovie;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemoveToWatchMovieController {
  private final RemoveToWatchMovie removeToWatchMovie;

  @Autowired
  public RemoveToWatchMovieController(RemoveToWatchMovie removeToWatchMovie) {
    this.removeToWatchMovie = removeToWatchMovie;
  }

  @Operation(summary = "Mark the movie as a one to watch.")
  @DeleteMapping("/{id}/watchlist")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeToWatch(@PathVariable long id) {
    removeToWatchMovie.process(id);
  }
}
