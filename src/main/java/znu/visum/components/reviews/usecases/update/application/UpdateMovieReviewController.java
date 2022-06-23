package znu.visum.components.reviews.usecases.update.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.usecases.update.domain.UpdateMovieReviewService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(UpdateMovieReviewResponse.class)
public class UpdateMovieReviewController {
  private final UpdateMovieReviewService updateMovieReviewService;

  @Autowired
  public UpdateMovieReviewController(UpdateMovieReviewService updateMovieReviewService) {
    this.updateMovieReviewService = updateMovieReviewService;
  }

  @Operation(summary = "Update a movie review.")
  @PutMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.OK)
  public UpdateMovieReviewResponse update(
      @PathVariable long id,
      @Valid @RequestBody UpdateMovieReviewRequest updateMovieReviewRequest) {
    return UpdateMovieReviewResponse.from(
        updateMovieReviewService.update(
            Review.builder()
                .id(id)
                .content(updateMovieReviewRequest.getContent())
                .grade(updateMovieReviewRequest.getGrade())
                .build()));
  }
}
