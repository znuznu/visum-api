package znu.visum.components.people.actors.usecases.getpage.application;

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
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.usecases.getpage.domain.GetPageActorService;
import znu.visum.core.pagination.application.GetPageResponse;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SearchSpecification;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageActorController {
  private final GetPageActorService getPageActorService;

  @Autowired
  public GetPageActorController(GetPageActorService getPageActorService) {
    this.getPageActorService = getPageActorService;
  }

  @ApiOperation("Get a page of actors.")
  @GetMapping
  public GetPageResponse<ActorFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "%%") String search,
      @SortDefault Sort sort) {
    Specification<Actor> searchSpecification = SearchSpecification.parse(search);

    PageSearch<Actor> pageSearch =
        new PageSearch.Builder<Actor>()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return GetPageResponse.from(
        getPageActorService.findPage(pageSearch), ActorFromPageResponse::from);
  }
}
