package znu.visum.components.diary.usecases.query.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.diary.domain.models.DiaryMovie;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetDiaryByYearServiceImpl implements GetDiaryByYearService {
  private final MovieRepository movieRepository;

  @Autowired
  public GetDiaryByYearServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public List<DiaryMovie> getDiaryMovies(Year year, Integer grade, Long genreId) {
    int yearValue = year.getValue();

    List<Movie> moviesSeenDuringTheYear =
        this.movieRepository.findByDiaryFilters(year, grade, genreId);

    List<DiaryMovie> diaryMovies = new ArrayList<>();

    for (Movie movie : moviesSeenDuringTheYear) {
      for (MovieViewingHistory movieViewingHistory : movie.getViewingHistory()) {
        if (movieViewingHistory.getViewingDate().getYear() != yearValue) {
          continue;
        }

        diaryMovies.add(DiaryMovie.from(movie, movieViewingHistory.getId()));
      }
    }

    return diaryMovies;
  }
}
