package znu.visum.business.people.director.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.people.People;
import znu.visum.business.people.director.models.Director;
import znu.visum.business.people.director.services.DirectorService;
import znu.visum.core.pagination.PageSearch;
import znu.visum.core.pagination.PageWrapper;
import znu.visum.core.pagination.SearchSpecification;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Director.class)
public class DirectorController {
    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    @JsonView(PageWrapper.Views.Full.class)
    public PageWrapper<Director> fetchPage(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "") String search,
            @SortDefault Sort sort
    ) {
        Specification<Director> searchSpecification = SearchSpecification.parse(search);

        PageSearch<Director> pageSearch = new PageSearch.Builder<Director>()
                .search(searchSpecification)
                .offset(offset)
                .limit(limit)
                .sorting(sort)
                .build();

        return new PageWrapper<>(directorService.findPage(pageSearch));
    }

    @GetMapping("/{id}")
    @JsonView(People.Views.Full.class)
    public Director getDirector(@PathVariable long id) {
        return directorService
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Director with id '%d'", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Director create(@RequestBody Director director) {
        return directorService.create(director);
    }

    @PutMapping("/{id}")
    public Director updateOne(@PathVariable long id, @RequestBody Director director) {
        director.setId(id);
        return directorService.update(director);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        directorService.deleteById(id);
    }
}
