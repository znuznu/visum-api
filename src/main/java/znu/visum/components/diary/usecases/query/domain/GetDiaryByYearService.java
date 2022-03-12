package znu.visum.components.diary.usecases.query.domain;

import znu.visum.components.diary.domain.models.DiaryMovie;

import java.time.Year;
import java.util.List;

public interface GetDiaryByYearService {
  List<DiaryMovie> getDiaryMovies(Year year, Integer grade, Long genreId);
}
