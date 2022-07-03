package znu.visum.components.diary.usecases.query.application;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.diary.usecases.query.application.types.DiaryFiltersInput;
import znu.visum.components.diary.usecases.query.application.types.DiaryYear;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYear;

import java.time.Year;
import java.util.List;

@DgsComponent
public class DiaryQueryFetcher {
  private final GetDiaryByYear getDiaryByYear;

  @Autowired
  public DiaryQueryFetcher(GetDiaryByYear getDiaryByYear) {
    this.getDiaryByYear = getDiaryByYear;
  }

  @DgsQuery
  public DiaryYear diary(@InputArgument DiaryFiltersInput filters) {
    Year year = Year.of(filters.getYear());

    List<DiaryMovie> diaryMovies =
        this.getDiaryByYear.getDiaryMovies(year, filters.getGrade(), filters.getGenreId());

    return DiaryYear.from(year, diaryMovies);
  }
}
