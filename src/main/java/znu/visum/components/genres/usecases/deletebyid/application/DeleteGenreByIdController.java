package znu.visum.components.genres.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.deletebyid.domain.DeleteGenreById;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteGenreByIdController {
  private final DeleteGenreById deleteGenreById;

  @Autowired
  public DeleteGenreByIdController(DeleteGenreById deleteGenreById) {
    this.deleteGenreById = deleteGenreById;
  }

  @Operation(summary = "Delete a genre by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteGenreById.process(id);
  }
}
