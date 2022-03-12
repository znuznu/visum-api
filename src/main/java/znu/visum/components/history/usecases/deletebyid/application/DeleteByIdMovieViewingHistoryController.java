package znu.visum.components.history.usecases.deletebyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.deletebyid.domain.DeleteByIdMovieViewingHistoryService;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdMovieViewingHistoryController {
  private final DeleteByIdMovieViewingHistoryService deleteByIdMovieViewingHistoryService;

  @Autowired
  public DeleteByIdMovieViewingHistoryController(
      DeleteByIdMovieViewingHistoryService deleteByIdMovieViewingHistoryService) {
    this.deleteByIdMovieViewingHistoryService = deleteByIdMovieViewingHistoryService;
  }

  @ApiOperation("Delete a movie by his identifier.")
  @DeleteMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdMovieViewingHistoryService.deleteById(id);
  }
}
