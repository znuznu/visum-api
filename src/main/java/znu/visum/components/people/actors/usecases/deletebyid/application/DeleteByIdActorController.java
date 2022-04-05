package znu.visum.components.people.actors.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.people.actors.usecases.deletebyid.domain.DeleteByIdActorService;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdActorController {
  private final DeleteByIdActorService deleteByIdActorService;

  @Autowired
  public DeleteByIdActorController(DeleteByIdActorService deleteByIdActorService) {
    this.deleteByIdActorService = deleteByIdActorService;
  }

  @Operation(summary = "Delete an actor by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdActorService.deleteById(id);
  }
}
