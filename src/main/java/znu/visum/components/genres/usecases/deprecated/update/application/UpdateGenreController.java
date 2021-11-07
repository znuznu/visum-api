package znu.visum.components.genres.usecases.deprecated.update.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.deprecated.update.domain.UpdateGenreService;

@Deprecated
@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(UpdateGenreResponse.class)
public class UpdateGenreController {
  private final UpdateGenreService updateGenreService;

  @Autowired
  public UpdateGenreController(UpdateGenreService updateGenreService) {
    this.updateGenreService = updateGenreService;
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UpdateGenreResponse updateById(
      @PathVariable long id, @RequestBody UpdateGenreRequest updateGenreRequest) {
    updateGenreRequest.setId(id);
    return UpdateGenreResponse.from(updateGenreService.update(updateGenreRequest.toDomain()));
  }
}
