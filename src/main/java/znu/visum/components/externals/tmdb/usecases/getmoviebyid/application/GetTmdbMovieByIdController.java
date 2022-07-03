package znu.visum.components.externals.tmdb.usecases.getmoviebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetTmdbMovieByIdResponse.class)
public class GetTmdbMovieByIdController {

  private final GetTmdbMovieById getTmdbMovieById;

  @Autowired
  public GetTmdbMovieByIdController(GetTmdbMovieById getTmdbMovieById) {
    this.getTmdbMovieById = getTmdbMovieById;
  }

  @Operation(summary = "Get a TMDb movie by his identifier.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetTmdbMovieByIdResponse getTmdbMovieById(@PathVariable @Min(0) long id) {
    return GetTmdbMovieByIdResponse.from(getTmdbMovieById.process(id));
  }
}
