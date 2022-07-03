package znu.visum.components.movies.usecases.markastowatch.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.markastowatch.domain.MarkMovieAsToWatch;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkMovieAsToWatchController {
  private final MarkMovieAsToWatch markMovieAsToWatch;

  @Autowired
  public MarkMovieAsToWatchController(MarkMovieAsToWatch markMovieAsToWatch) {
    this.markMovieAsToWatch = markMovieAsToWatch;
  }

  @Operation(summary = "Mark the movie as a one to watch.")
  @PutMapping("/{id}/watchlist")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void markAsToWatch(@PathVariable long id) {
    markMovieAsToWatch.process(id);
  }
}
