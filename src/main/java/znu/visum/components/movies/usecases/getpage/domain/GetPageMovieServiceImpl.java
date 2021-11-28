package znu.visum.components.movies.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageMovieServiceImpl implements GetPageMovieService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetPageMovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public VisumPage<Movie> findPage(int limit, int offset, Sort sort, String search) {
    return movieRepository.findPage(limit, offset, sort, search);
  }
}
