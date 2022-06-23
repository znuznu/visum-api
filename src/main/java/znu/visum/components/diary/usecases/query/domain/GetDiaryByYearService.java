package znu.visum.components.diary.usecases.query.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetDiaryByYearService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetDiaryByYearService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<DiaryMovie> getDiaryMovies(Year year, Integer grade, Long genreId) {
    int yearValue = year.getValue();

    List<Movie> moviesSeenDuringTheYear =
        this.movieRepository.findByDiaryFilters(year, grade, genreId);

    List<DiaryMovie> diaryMovies = new ArrayList<>();

    for (Movie movie : moviesSeenDuringTheYear) {
      for (MovieViewingHistory movieViewingHistory : movie.getViewingHistory()) {
        if (movieViewingHistory.getViewingDate() == null
            || movieViewingHistory.getViewingDate().getYear() != yearValue) {
          continue;
        }

        diaryMovies.add(DiaryMovie.from(movie, movieViewingHistory.getId()));
      }
    }

    return diaryMovies;
  }
}
