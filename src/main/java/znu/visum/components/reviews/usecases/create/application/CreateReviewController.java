package znu.visum.components.reviews.usecases.create.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.domain.models.Review;
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

  @ApiOperation("Create a review for a given movie identifier.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateReviewResponse create(
      @Valid @RequestBody final CreateReviewRequest createReviewRequest) {
    return CreateReviewResponse.from(
        createReviewService.save(
            new Review(
                null,
                createReviewRequest.getContent(),
                null,
                null,
                createReviewRequest.getGrade(),
                new MovieFromReview(createReviewRequest.getMovieId(), null, null))));
  }
}
