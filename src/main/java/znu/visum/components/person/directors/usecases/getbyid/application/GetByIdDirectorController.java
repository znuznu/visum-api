package znu.visum.components.person.directors.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getbyid.application.GetByIdMovieResponse;
import znu.visum.components.person.directors.usecases.getbyid.domain.GetByIdDirector;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdMovieResponse.class)
public class GetByIdDirectorController {

  private final GetByIdDirector getByIdDirector;

  @Autowired
  public GetByIdDirectorController(GetByIdDirector getByIdDirector) {
    this.getByIdDirector = getByIdDirector;
  }

  @Operation(summary = "Get a director by his identifier.")
  @GetMapping("/{id}")
  public GetByIdDirectorResponse getDirectorById(@PathVariable long id) {
    return GetByIdDirectorResponse.from(getByIdDirector.process(id));
  }
}
