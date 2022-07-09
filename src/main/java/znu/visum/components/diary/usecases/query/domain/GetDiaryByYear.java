package znu.visum.components.diary.usecases.query.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieQueryRepository;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetDiaryByYear {
  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public GetDiaryByYear(MovieQueryRepository movieQueryRepository) {
    this.movieQueryRepository = movieQueryRepository;
  }

  public List<DiaryMovie> getDiaryMovies(Year year, Integer grade, Long genreId) {
    int yearValue = year.getValue();

    List<Movie> moviesSeenDuringTheYear =
        this.movieQueryRepository.findByDiaryFilters(year, grade, genreId);

    List<DiaryMovie> diaryMovies = new ArrayList<>();

    for (Movie movie : moviesSeenDuringTheYear) {
      for (ViewingEntry viewingEntry : movie.getViewingHistory().getEntries()) {
        if (viewingEntry.getDate() == null || viewingEntry.getDate().getYear() != yearValue) {
          continue;
        }

        diaryMovies.add(DiaryMovie.from(movie, viewingEntry.getId()));
      }
    }

    return diaryMovies;
  }
}
