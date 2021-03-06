package znu.visum.components.genres.usecases.getpage.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.getpage.domain.GetGenrePage;
import znu.visum.core.pagination.application.GetPageResponse;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetGenrePageController {
  private final GetGenrePage getGenrePage;

  @Autowired
  public GetGenrePageController(GetGenrePage getGenrePage) {
    this.getGenrePage = getGenrePage;
  }

  @Operation(summary = "Get a page of genres.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<GenreFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "type=%%") String search,
      @SortDefault Sort sort) {
    return GetPageResponse.from(
        getGenrePage.process(limit, offset, sort, search), GenreFromPageResponse::from);
  }
}
