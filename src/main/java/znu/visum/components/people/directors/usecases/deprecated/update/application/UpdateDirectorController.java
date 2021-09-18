package znu.visum.components.people.directors.usecases.deprecated.update.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.usecases.deprecated.update.domain.UpdateDirectorService;

@Deprecated
@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Director.class)
public class UpdateDirectorController {
  private final UpdateDirectorService updateDirectorService;

  @Autowired
  public UpdateDirectorController(UpdateDirectorService updateDirectorService) {
    this.updateDirectorService = updateDirectorService;
  }

  @PutMapping("/{id}")
  public UpdateDirectorResponse update(
      @PathVariable long id, @RequestBody UpdateDirectorRequest updateDirectorRequest) {
    updateDirectorRequest.setId(id);
    return UpdateDirectorResponse.from(
        updateDirectorService.update(updateDirectorRequest.toDomain()));
  }
}
