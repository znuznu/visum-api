package znu.visum.components.diary.usecases.query.application;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import znu.visum.components.diary.domain.Diary;
import znu.visum.components.diary.usecases.query.application.types.DiaryFiltersInput;
import znu.visum.components.diary.usecases.query.application.types.DiaryYear;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYear;
import znu.visum.components.movies.domain.DiaryFilters;

import java.time.Year;

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
    Integer grade = filters.getGrade();
    Long genreId = filters.getGenreId();

    var diaryFilters = DiaryFilters.builder().year(year).grade(grade).genreId(genreId).build();

    Diary diary = this.getDiaryByYear.getDiary(diaryFilters);

    return DiaryYear.from(diary);
  }
}
