package znu.visum.components.reviews.usecases.getpage.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.usecases.getpage.domain.GetPageMovieReviewService;
import znu.visum.core.pagination.application.GetPageResponse;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SearchSpecification;

@RestController
@RequestMapping(value = "/api/reviews/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageMovieReviewController {
  private final GetPageMovieReviewService getPageMovieReviewService;

  @Autowired
  public GetPageMovieReviewController(GetPageMovieReviewService getPageMovieReviewService) {
    this.getPageMovieReviewService = getPageMovieReviewService;
  }

  @ApiOperation("Get a page of reviews.")
  @GetMapping
  public GetPageResponse<ReviewFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "") String search,
      @SortDefault Sort sort) {
    Specification<Review> searchSpecification = SearchSpecification.parse(search);

    PageSearch<Review> pageSearch =
        new PageSearch.Builder<Review>()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return GetPageResponse.from(
        getPageMovieReviewService.findPage(pageSearch), ReviewFromPageResponse::from);
  }
}
