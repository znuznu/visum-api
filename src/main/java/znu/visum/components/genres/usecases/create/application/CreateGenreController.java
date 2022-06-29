package znu.visum.components.genres.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.create.domain.CreateGenre;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateGenreResponse.class)
public class CreateGenreController {
  private final CreateGenre createGenre;

  @Autowired
  public CreateGenreController(CreateGenre createGenre) {
    this.createGenre = createGenre;
  }

  @Operation(summary = "Create a genre.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateGenreResponse create(@Valid @RequestBody CreateGenreRequest createGenreRequest) {
    return CreateGenreResponse.from(createGenre.process(createGenreRequest.toDomain()));
  }
}
