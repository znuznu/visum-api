package znu.visum.components.person.actors.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.person.actors.usecases.deletebyid.domain.DeleteActorById;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteActorByIdController {
  private final DeleteActorById deleteActorById;

  @Autowired
  public DeleteActorByIdController(DeleteActorById deleteActorById) {
    this.deleteActorById = deleteActorById;
  }

  @Operation(summary = "Delete an actor by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteActorById.process(id);
  }
}
