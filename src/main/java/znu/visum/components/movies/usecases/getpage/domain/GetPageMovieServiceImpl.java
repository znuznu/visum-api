package znu.visum.components.movies.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Service
public class GetPageMovieServiceImpl implements GetPageMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetPageMovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public VisumPage<Movie> findPage(PageSearch<Movie> pageSearch) {
    return movieRepository.findPage(pageSearch);
  }
}
