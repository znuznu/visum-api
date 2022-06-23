package znu.visum.components.people.actors.usecases.deprecated.create.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.actors.usecases.deprecated.create.domain.CreateActorService;

@Deprecated
@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Actor.class)
public class CreateActorController {
  private final CreateActorService createActorService;

  @Autowired
  public CreateActorController(CreateActorService createActorService) {
    this.createActorService = createActorService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateActorResponse create(@RequestBody CreateActorRequest createActorRequest) {
    return CreateActorResponse.from(createActorService.save(createActorRequest.toDomain()));
  }
}
