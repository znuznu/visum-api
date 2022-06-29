package znu.visum.components.person.actors.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.person.actors.usecases.deletebyid.domain.DeleteByIdActor;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdActorController {
  private final DeleteByIdActor deleteByIdActor;

  @Autowired
  public DeleteByIdActorController(DeleteByIdActor deleteByIdActor) {
    this.deleteByIdActor = deleteByIdActor;
  }

  @Operation(summary = "Delete an actor by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdActor.process(id);
  }
}
