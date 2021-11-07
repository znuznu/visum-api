package znu.visum.components.people.actors.usecases.getbyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.people.actors.usecases.getbyid.domain.GetByIdActorService;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdActorResponse.class)
public class GetByIdActorController {
  private final GetByIdActorService getByIdActorService;

  @Autowired
  public GetByIdActorController(GetByIdActorService getByIdActorService) {
    this.getByIdActorService = getByIdActorService;
  }

  @ApiOperation("Get an actor by his identifier.")
  @GetMapping("/{id}")
  public GetByIdActorResponse getActorById(@PathVariable long id) {
    return GetByIdActorResponse.from(getByIdActorService.findById(id));
  }
}
