package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.getbyid.domain.GetByIdGenre;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdGenreResponse.class)
public class GetByIdGenreController {
  private final GetByIdGenre getByIdGenre;

  @Autowired
  public GetByIdGenreController(GetByIdGenre getByIdGenre) {
    this.getByIdGenre = getByIdGenre;
  }

  @Operation(summary = "Get a genre by his identifier.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetByIdGenreResponse getGenreById(@PathVariable long id) {
    return GetByIdGenreResponse.from(getByIdGenre.process(id));
  }
}
