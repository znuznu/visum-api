package znu.visum.components.externals.tmdb.usecases.getupcoming.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getupcoming.domain.GetUpcomingTmdbMoviesService;
import znu.visum.core.pagination.application.GetPageResponse;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies/upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetUpcomingTmdbMoviesResponse.class)
public class GetUpcomingTmdbMoviesController {

  private final GetUpcomingTmdbMoviesService getUpcomingTmdbMoviesService;

  @Autowired
  public GetUpcomingTmdbMoviesController(
      GetUpcomingTmdbMoviesService getUpcomingTmdbMoviesService) {
    this.getUpcomingTmdbMoviesService = getUpcomingTmdbMoviesService;
  }

  @Operation(summary = "Get upcoming TMDb movies.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<GetUpcomingTmdbMoviesResponse> getUpcomingMovies(
      @RequestParam @Min(1) Integer pageNumber) {
    return GetPageResponse.from(
        getUpcomingTmdbMoviesService.getUpcomingMovies(pageNumber),
        GetUpcomingTmdbMoviesResponse::from);
  }
}
