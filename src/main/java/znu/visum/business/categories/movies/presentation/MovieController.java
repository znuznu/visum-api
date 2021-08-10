package znu.visum.business.categories.movies.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.categories.movies.services.MovieService;
import znu.visum.core.pagination.PageSearch;
import znu.visum.core.pagination.PageWrapper;
import znu.visum.core.pagination.SearchSpecification;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Movie.class)
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @JsonView(PageWrapper.Views.Full.class)
    public PageWrapper<Movie> fetchPage(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "") String search,
            @SortDefault Sort sort
    ) {
        Specification<Movie> searchSpecification = SearchSpecification.parse(search);

        PageSearch<Movie> pageSearch = new PageSearch.Builder<Movie>()
                .search(searchSpecification)
                .offset(offset)
                .limit(limit)
                .sorting(sort)
                .build();

        return new PageWrapper<>(movieService.findPage(pageSearch));
    }

    @GetMapping("/{id}")
    @JsonView(Movie.Views.Full.class)
    public Movie getMovie(@PathVariable long id) {
        return movieService
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Movie with id '%d'", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createWithIds(@RequestBody Movie movie) {
        return movieService.createWithIds(movie);
    }

    @PostMapping("/names")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createWithNames(@RequestBody Movie movie) {
        return movieService.createWithNames(movie);
    }

    @PutMapping("/{id}")
    @JsonView(Movie.Views.Full.class)
    public Movie updateOne(@PathVariable long id, @RequestBody Movie movie) {
        movie.setId(id);
        return movieService.update(movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        movieService.deleteById(id);
    }
}
