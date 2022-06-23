package znu.visum.components.people.directors.usecases.deprecated.create.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.usecases.deprecated.create.domain.CreateDirectorService;

@Deprecated
@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Director.class)
public class CreateDirectorController {
  private final CreateDirectorService createDirectorService;

  @Autowired
  public CreateDirectorController(CreateDirectorService createDirectorService) {
    this.createDirectorService = createDirectorService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateDirectorResponse create(@RequestBody CreateDirectorRequest createDirectorRequest) {
    return CreateDirectorResponse.from(
        createDirectorService.save(createDirectorRequest.toDomain()));
  }
}
