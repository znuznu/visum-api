package znu.visum.components.externals.tmdb.usecases.getnowplaying.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.getnowplaying.domain.GetNowPlayingTmdbMovies;
import znu.visum.core.pagination.application.GetPageResponse;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies/now_playing", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetNowPlayingTmdbMoviesResponse.class)
public class GetNowPlayingTmdbMoviesController {

  private final GetNowPlayingTmdbMovies getNowPlayingTmdbMovies;

  @Autowired
  public GetNowPlayingTmdbMoviesController(GetNowPlayingTmdbMovies getNowPlayingTmdbMovies) {
    this.getNowPlayingTmdbMovies = getNowPlayingTmdbMovies;
  }

  @Operation(summary = "Get now playing TMDb movies.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<GetNowPlayingTmdbMoviesResponse> getNowPlaying(
      @RequestParam @Min(1) Integer pageNumber, @RequestParam @Nullable String region) {
    return GetPageResponse.from(
        getNowPlayingTmdbMovies.process(pageNumber, region), GetNowPlayingTmdbMoviesResponse::from);
  }
}
