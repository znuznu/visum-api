package znu.visum.components.genres.usecases.getpage.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.usecases.getpage.domain.GetPageGenreService;
import znu.visum.core.pagination.application.GetPageResponse;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SearchSpecification;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageGenreController {
  private final GetPageGenreService getPageGenreService;

  @Autowired
  public GetPageGenreController(GetPageGenreService getPageGenreService) {
    this.getPageGenreService = getPageGenreService;
  }

  @ApiOperation("Get a page of genres.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<GenreFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "") String search,
      @SortDefault Sort sort) {
    Specification<Genre> searchSpecification = SearchSpecification.parse(search);

    PageSearch<Genre> pageSearch =
        new PageSearch.Builder<Genre>()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return GetPageResponse.from(
        getPageGenreService.findPage(pageSearch), GenreFromPageResponse::from);
  }
}
