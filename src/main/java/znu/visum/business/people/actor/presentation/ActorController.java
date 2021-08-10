package znu.visum.business.people.actor.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.people.People;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.business.people.actor.services.ActorService;
import znu.visum.core.pagination.PageSearch;
import znu.visum.core.pagination.PageWrapper;
import znu.visum.core.pagination.SearchSpecification;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Actor.class)
public class ActorController {
    private final ActorService actorService;
    private final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    @JsonView(PageWrapper.Views.Full.class)
    public PageWrapper<Actor> fetchPage(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "%%") String search,
            @SortDefault Sort sort
    ) {
        Specification<Actor> searchSpecification = SearchSpecification.parse(search);

        PageSearch<Actor> pageSearch = new PageSearch.Builder<Actor>()
                .search(searchSpecification)
                .offset(offset)
                .limit(limit)
                .sorting(sort)
                .build();

        return new PageWrapper<>(actorService.findPage(pageSearch));
    }

    @GetMapping("/{id}")
    @JsonView(People.Views.Full.class)
    public Actor getActor(@PathVariable long id) {
        return actorService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Actor with id '%d'", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Actor create(@RequestBody Actor actor) {
        return actorService.create(actor);
    }

    @PutMapping("/{id}")
    public Actor updateOne(@PathVariable long id, @RequestBody Actor actor) {
        actor.setId(id);
        return actorService.update(actor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        actorService.deleteById(id);
    }
}

