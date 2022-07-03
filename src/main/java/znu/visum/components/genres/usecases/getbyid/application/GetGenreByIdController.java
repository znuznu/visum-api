package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.getbyid.domain.GetGenreById;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetGenreByIdResponse.class)
public class GetGenreByIdController {
  private final GetGenreById getGenreById;

  @Autowired
  public GetGenreByIdController(GetGenreById getGenreById) {
    this.getGenreById = getGenreById;
  }

  @Operation(summary = "Get a genre by his identifier.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetGenreByIdResponse getGenreById(@PathVariable long id) {
    return GetGenreByIdResponse.from(getGenreById.process(id));
  }
}
