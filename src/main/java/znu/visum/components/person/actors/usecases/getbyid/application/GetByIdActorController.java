package znu.visum.components.person.actors.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.person.actors.usecases.getbyid.domain.GetByIdActor;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdActorResponse.class)
public class GetByIdActorController {

  private final GetByIdActor getByIdActor;

  @Autowired
  public GetByIdActorController(GetByIdActor getByIdActor) {
    this.getByIdActor = getByIdActor;
  }

  @Operation(summary = "Get an actor by his identifier.")
  @GetMapping("/{id}")
  public GetByIdActorResponse getActorById(@PathVariable long id) {
    return GetByIdActorResponse.from(getByIdActor.process(id));
  }
}
