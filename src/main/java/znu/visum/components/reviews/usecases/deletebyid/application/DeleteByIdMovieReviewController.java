package znu.visum.components.reviews.usecases.deletebyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.usecases.deletebyid.domain.DeleteByIdMovieReviewService;

@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteByIdMovieReviewController {
  private final DeleteByIdMovieReviewService deleteByIdMovieReviewService;

  @Autowired
  public DeleteByIdMovieReviewController(
      DeleteByIdMovieReviewService deleteByIdMovieReviewService) {
    this.deleteByIdMovieReviewService = deleteByIdMovieReviewService;
  }

  @ApiOperation("Delete a review by his identifier.")
  @DeleteMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteByIdMovieReviewService.deleteById(id);
  }
}
