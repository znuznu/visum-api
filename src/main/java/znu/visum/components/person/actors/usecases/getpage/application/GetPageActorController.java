package znu.visum.components.person.actors.usecases.getpage.application;

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
import znu.visum.components.person.actors.usecases.getpage.domain.GetPageActor;
import znu.visum.core.pagination.application.GetPageResponse;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class GetPageActorController {

  private final GetPageActor getPageActor;

  @Autowired
  public GetPageActorController(GetPageActor getPageActor) {
    this.getPageActor = getPageActor;
  }

  @Operation(summary = "Get a page of actors.")
  @GetMapping
  public GetPageResponse<ActorFromPageResponse> getPage(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false, defaultValue = "forename=%%,name=%%") String search,
      @SortDefault Sort sort) {
    return GetPageResponse.from(
        getPageActor.process(limit, offset, sort, search), ActorFromPageResponse::from);
  }
}
