package znu.visum.components.movies.usecases.removefavorite.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.removefavorite.domain.RemoveFavoriteMovieService;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemoveFavoriteMovieController {
  private final RemoveFavoriteMovieService removeFavoriteMovieService;

  @Autowired
  public RemoveFavoriteMovieController(RemoveFavoriteMovieService removeFavoriteMovieService) {
    this.removeFavoriteMovieService = removeFavoriteMovieService;
  }

  @ApiOperation("Mark the movie as a favorite one.")
  @DeleteMapping("/{id}/favorite")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFavorite(@PathVariable long id) {
    removeFavoriteMovieService.removeFavorite(id);
  }
}
