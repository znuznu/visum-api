package znu.visum.business.genres.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.genres.models.Genre;
import znu.visum.business.genres.services.GenreService;
import znu.visum.core.pagination.PageSearch;
import znu.visum.core.pagination.PageWrapper;
import znu.visum.core.pagination.SearchSpecification;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Genre.class)
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @JsonView(PageWrapper.Views.Full.class)
    @ResponseStatus(HttpStatus.OK)
    public PageWrapper<Genre> fetchPage(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "") String search,
            @SortDefault Sort sort
    ) {
        Specification<Genre> searchSpecification = SearchSpecification.parse(search);

        PageSearch<Genre> pageSearch = new PageSearch.Builder<Genre>()
                .search(searchSpecification)
                .offset(offset)
                .limit(limit)
                .sorting(sort)
                .build();

        return new PageWrapper<>(genreService.findPage(pageSearch));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getGenre(@PathVariable long id) {
        return genreService
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Genre with id '%d'", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genre create(@RequestBody Genre genre) {
        return genreService.create(genre);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre updateOne(@PathVariable long id, @RequestBody Genre genre) {
        genre.setId(id);
        return genreService.update(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        genreService.deleteById(id);
    }
}
