package znu.visum.components.externals.tmdb.usecases.getupcoming.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getupcoming.domain.GetUpcomingTmdbMovies;
import znu.visum.core.pagination.application.GetPageResponse;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies/upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetUpcomingTmdbMoviesResponse.class)
public class GetUpcomingTmdbMoviesController {

  private final GetUpcomingTmdbMovies getUpcomingTmdbMovies;

  @Autowired
  public GetUpcomingTmdbMoviesController(GetUpcomingTmdbMovies getUpcomingTmdbMovies) {
    this.getUpcomingTmdbMovies = getUpcomingTmdbMovies;
  }

  @Operation(summary = "Get upcoming TMDb movies.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<GetUpcomingTmdbMoviesResponse> getUpcomingMovies(
      @RequestParam @Min(1) Integer pageNumber, @RequestParam @Nullable String region) {
    return GetPageResponse.from(
        getUpcomingTmdbMovies.process(pageNumber, region), GetUpcomingTmdbMoviesResponse::from);
  }
}
