package znu.visum.components.external.tmdb.usecases.searchmovies.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.external.tmdb.usecases.searchmovies.domain.SearchTmdbMoviesService;
import znu.visum.core.pagination.application.GetPageResponse;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

// TODO Valid the input

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies/search", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class SearchTmdbMoviesController {
  private final SearchTmdbMoviesService searchTmdbMoviesService;

  @Autowired
  public SearchTmdbMoviesController(SearchTmdbMoviesService searchTmdbMoviesService) {
    this.searchTmdbMoviesService = searchTmdbMoviesService;
  }

  @ApiOperation("Search movies on TMDB.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<SearchTmdbMoviesResponse> searchTmdbMovies(
      @RequestParam @NotBlank String search, @RequestParam @Min(1) Integer pageNumber) {
    return GetPageResponse.from(
        searchTmdbMoviesService.searchMovies(search, pageNumber), SearchTmdbMoviesResponse::from);
  }
}
