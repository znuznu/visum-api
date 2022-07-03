package znu.visum.components.movies.usecases.markasfavorite.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.markasfavorite.domain.MarkMovieAsFavorite;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkMovieAsFavoriteController {
  private final MarkMovieAsFavorite markMovieAsFavorite;

  @Autowired
  public MarkMovieAsFavoriteController(MarkMovieAsFavorite markMovieAsFavorite) {
    this.markMovieAsFavorite = markMovieAsFavorite;
  }

  @Operation(summary = "Mark the movie as a favorite one.")
  @PutMapping("/{id}/favorite")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void markAsFavorite(@PathVariable long id) {
    markMovieAsFavorite.process(id);
  }
}
