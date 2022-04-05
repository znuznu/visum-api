package znu.visum.components.movies.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.deletebyid.domain.DeleteByIdMovieService;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdMovieController {
  private final DeleteByIdMovieService deleteByIdMovieService;

  @Autowired
  public DeleteByIdMovieController(DeleteByIdMovieService deleteByIdMovieService) {
    this.deleteByIdMovieService = deleteByIdMovieService;
  }

  @Operation(summary = "Delete a Movie by his identifier.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdMovieService.deleteById(id);
  }
}
