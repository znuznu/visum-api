package znu.visum.components.movies.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageMovie {
  private final MovieRepository movieRepository;

  @Autowired
  public GetPageMovie(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public VisumPage<Movie> process(int limit, int offset, Sort sort, String search) {
    return movieRepository.findPage(limit, offset, sort, search);
  }
}
