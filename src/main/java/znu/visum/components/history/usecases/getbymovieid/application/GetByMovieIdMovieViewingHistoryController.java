package znu.visum.components.history.usecases.getbymovieid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.usecases.getbymovieid.domain.GetByMovieIdMovieViewingHistory;

import java.util.List;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(MovieViewingHistory.class)
public class GetByMovieIdMovieViewingHistoryController {

  private final GetByMovieIdMovieViewingHistory getByMovieIdMovieViewingHistory;

  @Autowired
  public GetByMovieIdMovieViewingHistoryController(
      GetByMovieIdMovieViewingHistory getByMovieIdMovieViewingHistory) {
    this.getByMovieIdMovieViewingHistory = getByMovieIdMovieViewingHistory;
  }

  @GetMapping("/movies/{id}")
  public List<MovieViewingHistory> getByMovieId(@PathVariable long id) {
    return getByMovieIdMovieViewingHistory.process(id);
  }
}
