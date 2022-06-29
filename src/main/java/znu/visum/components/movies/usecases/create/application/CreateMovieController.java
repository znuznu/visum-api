package znu.visum.components.movies.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.movies.usecases.create.domain.CreateMovie;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateMovieResponse.class)
public class CreateMovieController {
  private final CreateMovie createMovie;

  @Autowired
  public CreateMovieController(CreateMovie createMovie) {
    this.createMovie = createMovie;
  }

  @Operation(summary = "Creates a movie based on a TMDb id.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateMovieResponse createMovie(
      @Valid @RequestBody CreateMovieRequest createMovieRequest) {
    return CreateMovieResponse.from(createMovie.process(createMovieRequest.toCommand()));
  }
}
