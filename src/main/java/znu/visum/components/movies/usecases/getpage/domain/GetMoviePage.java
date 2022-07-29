package znu.visum.components.movies.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetMoviePage {
  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public GetMoviePage(MovieQueryRepository movieQueryRepository) {
    this.movieQueryRepository = movieQueryRepository;
  }

  public VisumPage<PageMovie> process(int limit, int offset, Sort sort, String search) {
    return movieQueryRepository.findPage(limit, offset, sort, search);
  }
}
