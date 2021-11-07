package znu.visum.components.reviews.usecases.deprecated.getbyid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.usecases.deprecated.getbyid.domain.GetByIdMovieReviewService;

@Deprecated
@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Review.class)
public class GetByIdMovieReviewController {
  private final GetByIdMovieReviewService getByIdMovieReviewService;

  @Autowired
  public GetByIdMovieReviewController(GetByIdMovieReviewService getByIdMovieReviewService) {
    this.getByIdMovieReviewService = getByIdMovieReviewService;
  }

  @GetMapping("/{id}/movies")
  public GetByIdMovieReviewResponse getMovieReviewById(@PathVariable long id) {
    Review review = getByIdMovieReviewService.findById(id);

    return GetByIdMovieReviewResponse.from(review);
  }
}
