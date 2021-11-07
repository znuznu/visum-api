package znu.visum.components.genres.usecases.deletebyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.deletebyid.domain.DeleteByIdGenreService;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdGenreController {
  private final DeleteByIdGenreService deleteByIdGenreService;

  @Autowired
  public DeleteByIdGenreController(DeleteByIdGenreService deleteByIdGenreService) {
    this.deleteByIdGenreService = deleteByIdGenreService;
  }

  @ApiOperation("Delete a genre bis his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdGenreService.deleteById(id);
  }
}
