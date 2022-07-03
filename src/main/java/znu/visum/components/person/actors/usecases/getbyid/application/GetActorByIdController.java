package znu.visum.components.person.actors.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.person.actors.usecases.getbyid.domain.GetActorById;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetActorByIdResponse.class)
public class GetActorByIdController {

  private final GetActorById getActorById;

  @Autowired
  public GetActorByIdController(GetActorById getActorById) {
    this.getActorById = getActorById;
  }

  @Operation(summary = "Get an actor by his identifier.")
  @GetMapping("/{id}")
  public GetActorByIdResponse getActorById(@PathVariable long id) {
    return GetActorByIdResponse.from(getActorById.process(id));
  }
}
