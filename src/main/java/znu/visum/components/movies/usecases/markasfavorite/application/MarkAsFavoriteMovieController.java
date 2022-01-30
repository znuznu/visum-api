package znu.visum.components.movies.usecases.markasfavorite.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.markasfavorite.domain.MarkAsFavoriteMovieService;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkAsFavoriteMovieController {
  private final MarkAsFavoriteMovieService markAsFavoriteMovieService;

  @Autowired
  public MarkAsFavoriteMovieController(MarkAsFavoriteMovieService markAsFavoriteMovieService) {
    this.markAsFavoriteMovieService = markAsFavoriteMovieService;
  }

  @ApiOperation("Mark the movie as a favorite one.")
  @PutMapping("/{id}/favorite")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void markAsFavorite(@PathVariable long id) {
    markAsFavoriteMovieService.markAsFavorite(id);
  }
}
