package znu.visum.components.movies.usecases.removeastowatch.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.removeastowatch.domain.RemoveMovieAsToWatch;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemoveMovieAsToWatchController {
  private final RemoveMovieAsToWatch removeMovieAsToWatch;

  @Autowired
  public RemoveMovieAsToWatchController(RemoveMovieAsToWatch removeMovieAsToWatch) {
    this.removeMovieAsToWatch = removeMovieAsToWatch;
  }

  @Operation(summary = "Mark the movie as a one to watch.")
  @DeleteMapping("/{id}/watchlist")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeToWatch(@PathVariable long id) {
    removeMovieAsToWatch.process(id);
  }
}
