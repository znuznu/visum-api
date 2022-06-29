package znu.visum.components.person.directors.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.person.directors.usecases.deletebyid.domain.DeleteByIdDirector;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdDirectorController {

  private final DeleteByIdDirector deleteByIdDirector;

  @Autowired
  public DeleteByIdDirectorController(DeleteByIdDirector deleteByIdDirector) {
    this.deleteByIdDirector = deleteByIdDirector;
  }

  @Operation(summary = "Delete a director by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdDirector.process(id);
  }
}
