package znu.visum.components.reviews.usecases.deletebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.usecases.deletebyid.domain.DeleteReviewById;

@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteReviewByIdController {

  private final DeleteReviewById deleteReviewById;

  @Autowired
  public DeleteReviewByIdController(DeleteReviewById deleteReviewById) {
    this.deleteReviewById = deleteReviewById;
  }

  @Operation(summary = "Delete a review by his identifier.")
  @DeleteMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    deleteReviewById.deleteById(id);
  }
}
