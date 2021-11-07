package znu.visum.components.movies.usecases.create.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.create.domain.CreateMovieService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateMovieResponse.class)
public class CreateMovieController {
  private final CreateMovieService createMovieService;

  @Autowired
  public CreateMovieController(CreateMovieService createMovieService) {
    this.createMovieService = createMovieService;
  }

  @ApiOperation("Create a movie with cast, genres, TMDB identifier and a viewing history.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateMovieResponse createWithNames(
      @Valid @RequestBody CreateMovieRequest createMovieRequest) {
    return CreateMovieResponse.from(
        createMovieService.saveWithNameFromPeople(createMovieRequest.toDomain()));
  }
}
