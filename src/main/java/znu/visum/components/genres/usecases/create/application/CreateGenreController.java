package znu.visum.components.genres.usecases.create.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.genres.usecases.create.domain.CreateGenreService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateGenreResponse.class)
public class CreateGenreController {
  private final CreateGenreService createGenreService;

  @Autowired
  public CreateGenreController(CreateGenreService createGenreService) {
    this.createGenreService = createGenreService;
  }

  @ApiOperation("Create a genre.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateGenreResponse create(@Valid @RequestBody CreateGenreRequest createGenreRequest) {
    return CreateGenreResponse.from(createGenreService.save(createGenreRequest.toDomain()));
  }
}
