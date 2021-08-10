package znu.visum.business.tmdb.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.MovieDetail;
import znu.visum.business.tmdb.mappers.MoviePreviewResults;
import znu.visum.business.tmdb.models.CustomMovie;
import znu.visum.business.tmdb.services.TmdbMovieService;
import znu.visum.business.tmdb.services.TmdbSearchService;

@RestController
@RequestMapping(value = "/api/tmdb", produces = MediaType.APPLICATION_JSON_VALUE)
public class TmdbController {
    private final TmdbMovieService tmdbMovieService;
    private final TmdbSearchService tmdbSearchService;

    @Autowired
    public TmdbController(TmdbMovieService tmdbMovieService, TmdbSearchService tmdbSearchService) {
        this.tmdbMovieService = tmdbMovieService;
        this.tmdbSearchService = tmdbSearchService;
    }

    @GetMapping("/movies")
    @ResponseStatus(HttpStatus.OK)
    public MoviePreviewResults getMoviePreview(@RequestParam(required = true) String search,
                                               @RequestParam(required = false) Integer page) throws JsonProcessingException {
        return tmdbSearchService.findPreviewsByTitle(search, page != null ? page : 1);
    }

    @GetMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomMovie getMovieDetail(@PathVariable int id) {
        MovieDetail movieDetail = tmdbMovieService.findDetailById(id).orElseThrow();
        Credits credits = tmdbMovieService.findMovieCreditsById(id).orElseThrow();

        return new CustomMovie(movieDetail, credits);
    }
}
