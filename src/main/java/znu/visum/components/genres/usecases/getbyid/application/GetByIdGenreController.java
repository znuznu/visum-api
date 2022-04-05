package znu.visum.components.genres.usecases.getbyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.getbyid.domain.GetByIdGenreService;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdGenreResponse.class)
public class GetByIdGenreController {
  private final GetByIdGenreService getByIdGenreService;

  @Autowired
  public GetByIdGenreController(GetByIdGenreService getByIdGenreService) {
    this.getByIdGenreService = getByIdGenreService;
  }

  @Operation(summary = "Get a genre by his identifier.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetByIdGenreResponse getGenreById(@PathVariable long id) {
    return GetByIdGenreResponse.from(getByIdGenreService.findById(id));
  }
}
