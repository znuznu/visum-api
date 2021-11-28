package znu.visum.components.people.directors.usecases.getpage.application;

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
import znu.visum.components.people.directors.usecases.getpage.domain.GetPageDirectorService;
import znu.visum.core.pagination.application.GetPageResponse;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageDirectorController {
  private final GetPageDirectorService getPageDirectorService;

  @Autowired
  public GetPageDirectorController(GetPageDirectorService getPageDirectorService) {
    this.getPageDirectorService = getPageDirectorService;
  }

  @ApiOperation("Get a page of directors.")
  @GetMapping
  public GetPageResponse<DirectorFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "forename=%%,name=%%") String search,
      @SortDefault Sort sort) {
    return GetPageResponse.from(
        getPageDirectorService.findPage(limit, offset, sort, search),
        DirectorFromPageResponse::from);
  }
}
