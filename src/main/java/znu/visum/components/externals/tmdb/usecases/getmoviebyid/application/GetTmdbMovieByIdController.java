package znu.visum.components.externals.tmdb.usecases.getmoviebyid.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieByIdService;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetTmdbMovieByIdResponse.class)
public class GetTmdbMovieByIdController {
  private final GetTmdbMovieByIdService getTmdbMovieByIdService;

  @Autowired
  public GetTmdbMovieByIdController(GetTmdbMovieByIdService getTmdbMovieByIdService) {
    this.getTmdbMovieByIdService = getTmdbMovieByIdService;
  }

  @Operation(summary = "Get a TMDB movie by his identifier.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetTmdbMovieByIdResponse getTmdbMovieById(@PathVariable @Min(0) long id) {
    return GetTmdbMovieByIdResponse.from(getTmdbMovieByIdService.getTmdbMovieById(id));
  }
}
