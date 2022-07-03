package znu.visum.components.movies.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getbyid.domain.GetMovieById;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetMovieByIdResponse.class)
public class GetMovieByIdController {
  private final GetMovieById getMovieById;

  @Autowired
  public GetMovieByIdController(GetMovieById getMovieById) {
    this.getMovieById = getMovieById;
  }

  @Operation(summary = "Get a movie by his identifier.")
  @GetMapping("/{id}")
  public GetMovieByIdResponse getMovieById(@PathVariable long id) {
    return GetMovieByIdResponse.from(getMovieById.process(id));
  }
}
