package znu.visum.components.movies.usecases.getpage.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getpage.domain.GetPageMovieService;
import znu.visum.core.pagination.application.GetPageResponse;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageMovieController {
  private final GetPageMovieService getPageMovieService;

  @Autowired
  public GetPageMovieController(GetPageMovieService getPageMovieService) {
    this.getPageMovieService = getPageMovieService;
  }

  @ApiOperation("Get a page of movies.")
  @GetMapping
  public GetPageResponse<MovieFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "title=%%") String search,
      @SortDefault Sort sort) {
    return GetPageResponse.from(
        getPageMovieService.findPage(limit, offset, sort, search), MovieFromPageResponse::from);
  }
}
