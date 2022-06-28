package znu.visum.components.reviews.usecases.update.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.usecases.update.domain.UpdateReview;
import znu.visum.components.reviews.usecases.update.domain.UpdateReviewCommand;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(UpdateReviewResponse.class)
public class UpdateReviewController {
  private final UpdateReview usecase;

  @Autowired
  public UpdateReviewController(UpdateReview usecase) {
    this.usecase = usecase;
  }

  @Operation(summary = "Update a movie review.")
  @PutMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.OK)
  public UpdateReviewResponse update(
      @PathVariable long id, @Valid @RequestBody UpdateReviewRequest request) {
    var command =
        UpdateReviewCommand.builder()
            .id(id)
            .content(request.getContent())
            .grade(request.getGrade())
            .build();

    return UpdateReviewResponse.from(usecase.process(command));
  }
}
