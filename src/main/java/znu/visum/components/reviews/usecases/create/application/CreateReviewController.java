package znu.visum.components.reviews.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.usecases.create.domain.CreateReview;
import znu.visum.components.reviews.usecases.create.domain.CreateReviewCommand;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/reviews/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateReviewResponse.class)
public class CreateReviewController {
  private final CreateReview createReview;

  @Autowired
  public CreateReviewController(CreateReview createReview) {
    this.createReview = createReview;
  }

  @Operation(summary = "Create a review for a given movie identifier.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateReviewResponse create(
      @Valid @RequestBody final CreateReviewRequest createReviewRequest) {
    return CreateReviewResponse.from(
        createReview.process(
            CreateReviewCommand.builder()
                .content(createReviewRequest.getContent())
                .grade(createReviewRequest.getGrade())
                .movieId(createReviewRequest.getMovieId())
                .build()));
  }
}
