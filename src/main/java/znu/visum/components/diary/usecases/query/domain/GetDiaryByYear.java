package znu.visum.components.diary.usecases.query.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.diary.domain.Diary;
import znu.visum.components.movies.domain.DiaryFilters;
import znu.visum.components.movies.domain.MovieDiaryFragment;
import znu.visum.components.movies.domain.MovieQueryRepository;

import java.util.List;

@Service
public class GetDiaryByYear {

  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public GetDiaryByYear(MovieQueryRepository movieQueryRepository) {
    this.movieQueryRepository = movieQueryRepository;
  }

  public Diary getDiary(DiaryFilters filters) {
    List<MovieDiaryFragment> moviesSeenDuringTheYear =
        this.movieQueryRepository.findByDiaryFilters(filters);

    return new Diary(filters.getYear(), moviesSeenDuringTheYear);
  }
}
