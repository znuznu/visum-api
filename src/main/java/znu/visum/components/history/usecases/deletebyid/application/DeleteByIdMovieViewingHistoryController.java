package znu.visum.components.history.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.deletebyid.domain.DeleteByIdMovieViewingHistory;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdMovieViewingHistoryController {
  private final DeleteByIdMovieViewingHistory deleteByIdMovieViewingHistory;

  @Autowired
  public DeleteByIdMovieViewingHistoryController(
      DeleteByIdMovieViewingHistory deleteByIdMovieViewingHistory) {
    this.deleteByIdMovieViewingHistory = deleteByIdMovieViewingHistory;
  }

  @Operation(summary = "Delete a movie by his identifier.")
  @DeleteMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdMovieViewingHistory.process(id);
  }
}
