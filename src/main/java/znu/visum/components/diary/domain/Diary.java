package znu.visum.components.diary.domain;

import lombok.Getter;
import znu.visum.components.movies.domain.MovieDiaryFragment;
import znu.visum.core.assertions.VisumAssert;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Diary {

  private final Year year;

  private final Map<Month, DiaryMonth> months;

  public Diary(Year year, List<MovieDiaryFragment> movies) {
    VisumAssert.notNull("year", year);
    VisumAssert.notNull("movies", movies);

    this.year = year;
    this.months = new HashMap<>();
    this.months.put(Month.JANUARY, DiaryMonth.of(Month.JANUARY));
    this.months.put(Month.FEBRUARY, DiaryMonth.of(Month.FEBRUARY));
    this.months.put(Month.MARCH, DiaryMonth.of(Month.MARCH));
    this.months.put(Month.APRIL, DiaryMonth.of(Month.APRIL));
    this.months.put(Month.MAY, DiaryMonth.of(Month.MAY));
    this.months.put(Month.JUNE, DiaryMonth.of(Month.JUNE));
    this.months.put(Month.JULY, DiaryMonth.of(Month.JULY));
    this.months.put(Month.AUGUST, DiaryMonth.of(Month.AUGUST));
    this.months.put(Month.SEPTEMBER, DiaryMonth.of(Month.SEPTEMBER));
    this.months.put(Month.OCTOBER, DiaryMonth.of(Month.OCTOBER));
    this.months.put(Month.NOVEMBER, DiaryMonth.of(Month.NOVEMBER));
    this.months.put(Month.DECEMBER, DiaryMonth.of(Month.DECEMBER));

    for (MovieDiaryFragment movie : movies) {
      var splits = movie.splitAsDiaryMovies();

      splits.stream()
          .filter(entry -> entry.isOfYear(this.year))
          .forEach(entry -> months.get(entry.getViewingDate().getMonth()).addAll(entry));
    }
  }

  public DiaryMonth getJanuary() {
    return this.months.get(Month.JANUARY);
  }

  public DiaryMonth getFebruary() {
    return this.months.get(Month.FEBRUARY);
  }

  public DiaryMonth getMarch() {
    return this.months.get(Month.MARCH);
  }

  public DiaryMonth getApril() {
    return this.months.get(Month.APRIL);
  }

  public DiaryMonth getMay() {
    return this.months.get(Month.MAY);
  }

  public DiaryMonth getJune() {
    return this.months.get(Month.JUNE);
  }

  public DiaryMonth getJuly() {
    return this.months.get(Month.JULY);
  }

  public DiaryMonth getAugust() {
    return this.months.get(Month.AUGUST);
  }

  public DiaryMonth getSeptember() {
    return this.months.get(Month.SEPTEMBER);
  }

  public DiaryMonth getOctober() {
    return this.months.get(Month.OCTOBER);
  }

  public DiaryMonth getNovember() {
    return this.months.get(Month.NOVEMBER);
  }

  public DiaryMonth getDecember() {
    return this.months.get(Month.DECEMBER);
  }
}
