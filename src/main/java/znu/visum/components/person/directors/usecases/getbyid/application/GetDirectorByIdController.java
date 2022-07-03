package znu.visum.components.person.directors.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getbyid.application.GetMovieByIdResponse;
import znu.visum.components.person.directors.usecases.getbyid.domain.GetDirectorById;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetMovieByIdResponse.class)
public class GetDirectorByIdController {

  private final GetDirectorById getDirectorById;

  @Autowired
  public GetDirectorByIdController(GetDirectorById getDirectorById) {
    this.getDirectorById = getDirectorById;
  }

  @Operation(summary = "Get a director by his identifier.")
  @GetMapping("/{id}")
  public GetDirectorByIdResponse getDirectorById(@PathVariable long id) {
    return GetDirectorByIdResponse.from(getDirectorById.process(id));
  }
}
