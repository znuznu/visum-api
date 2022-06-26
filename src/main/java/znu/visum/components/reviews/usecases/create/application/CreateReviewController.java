package znu.visum.components.reviews.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.components.reviews.domain.MovieFromReview;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.usecases.create.domain.CreateReviewService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/reviews/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateReviewResponse.class)
public class CreateReviewController {
  private final CreateReviewService createReviewService;

  @Autowired
  public CreateReviewController(CreateReviewService createReviewService) {
    this.createReviewService = createReviewService;
  }

  @Operation(summary = "Create a review for a given movie identifier.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateReviewResponse create(
      @Valid @RequestBody final CreateReviewRequest createReviewRequest) {
    return CreateReviewResponse.from(
        createReviewService.save(
            Review.builder()
                .content(createReviewRequest.getContent())
                .grade(new Grade(createReviewRequest.getGrade()))
                .movie(MovieFromReview.builder().id(createReviewRequest.getMovieId()).build())
                .build()));
  }
}
