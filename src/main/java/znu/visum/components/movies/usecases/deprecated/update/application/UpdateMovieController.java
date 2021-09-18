package znu.visum.components.movies.usecases.deprecated.update.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.usecases.deprecated.update.domain.UpdateMovieService;

@Deprecated
@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Movie.class)
public class UpdateMovieController {
  private final UpdateMovieService updateMovieService;

  @Autowired
  public UpdateMovieController(UpdateMovieService updateMovieService) {
    this.updateMovieService = updateMovieService;
  }

  @PutMapping("/{id}")
  public UpdateMovieResponse update(
      @PathVariable long id, @RequestBody UpdateMovieRequest updateMovieRequest) {
    updateMovieRequest.setId(id);

    var domain = updateMovieRequest.toDomain();

    var updated = updateMovieService.update(domain);

    return UpdateMovieResponse.from(updated);
  }
}
