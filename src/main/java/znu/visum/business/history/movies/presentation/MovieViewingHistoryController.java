package znu.visum.business.history.movies.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.history.movies.models.MovieViewingHistory;
import znu.visum.business.history.movies.services.MovieViewingHistoryService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(MovieViewingHistory.class)
public class MovieViewingHistoryController {
    private final MovieViewingHistoryService movieViewingHistoryService;

    @Autowired
    public MovieViewingHistoryController(MovieViewingHistoryService movieViewingHistoryService) {
        this.movieViewingHistoryService = movieViewingHistoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieViewingHistory create(@RequestBody MovieViewingHistory movieViewingHistory) {
        return movieViewingHistoryService.save(movieViewingHistory);
    }

    @GetMapping("/{id}/movies")
    @JsonView(MovieViewingHistory.Views.Full.class)
    public MovieViewingHistory getById(@PathVariable long id) {
        return movieViewingHistoryService
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No viewing history with id '%d'", id))
                );
    }

    @GetMapping("/movies/{id}")
    @JsonView(MovieViewingHistory.Views.Full.class)
    public List<MovieViewingHistory> getByMovieId(@PathVariable long id) {
        return movieViewingHistoryService.findByMovieId(id);
    }

    @DeleteMapping("/{id}/movies")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        movieViewingHistoryService.deleteById(id);
    }
}
