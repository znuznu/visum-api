package znu.visum.components.reviews.usecases.getpage.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.reviews.usecases.getpage.domain.GetPageMovieReviewService;
import znu.visum.core.pagination.application.GetPageResponse;

@RestController
@RequestMapping(value = "/api/reviews/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageMovieReviewController {
  private final GetPageMovieReviewService getPageMovieReviewService;

  @Autowired
  public GetPageMovieReviewController(GetPageMovieReviewService getPageMovieReviewService) {
    this.getPageMovieReviewService = getPageMovieReviewService;
  }

  @Operation(summary = "Get a page of reviews.")
  @GetMapping
  public GetPageResponse<ReviewFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "content=%%") String search,
      @SortDefault Sort sort) {
    return GetPageResponse.from(
        getPageMovieReviewService.findPage(limit, offset, sort, search),
        ReviewFromPageResponse::from);
  }
}
