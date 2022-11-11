package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.application;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain.GetTmdbMoviesByDirectorId;
import znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain.GetTmdbMoviesByDirectorIdQuery;

import javax.validation.constraints.Min;

@Validated
@RestController
@Slf4j
@RequestMapping(value = "/api/tmdb/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetTmdbMoviesByDirectorIdResponse.class)
public class GetTmdbMoviesByDirectorIdController {

  private final GetTmdbMoviesByDirectorId getMoviesByDirector;

  @Autowired
  public GetTmdbMoviesByDirectorIdController(GetTmdbMoviesByDirectorId getMoviesByDirector) {
    this.getMoviesByDirector = getMoviesByDirector;
  }

  @Operation(summary = "Get TMDb movies directed by the director related to the given id.")
  @GetMapping("/{id}/movies")
  @ResponseStatus(HttpStatus.OK)
  public GetTmdbMoviesByDirectorIdResponse getMoviesByDirector(
      @PathVariable @Min(0) Integer id,
      @RequestParam(value = "notSavedOnly", required = false, defaultValue = "false")
          boolean notSavedOnly) {
    return GetTmdbMoviesByDirectorIdResponse.from(
        getMoviesByDirector.process(new GetTmdbMoviesByDirectorIdQuery(id, notSavedOnly)));
  }
}
