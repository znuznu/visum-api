package znu.visum.components.diary.usecases.query.application.types;

import lombok.Getter;
import znu.visum.components.diary.domain.models.DiaryMovie;
import znu.visum.core.models.common.Month;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class DiaryYear {

  private static final List<Month> MONTH_VALUES =
      List.of(
          Month.DECEMBER,
          Month.NOVEMBER,
          Month.OCTOBER,
          Month.SEPTEMBER,
          Month.AUGUST,
          Month.JULY,
          Month.JUNE,
          Month.MAY,
          Month.APRIL,
          Month.MARCH,
          Month.FEBRUARY,
          Month.JANUARY);
  private int year;
  private List<DiaryMonth> months;

  public DiaryYear() {}

  public DiaryYear(int year, List<DiaryMonth> months) {
    this.months = months;
    this.year = year;
  }

  public static DiaryYear from(Year year, List<DiaryMovie> movies) {
    int yearValue = year.getValue();

    Map<znu.visum.core.models.common.Month, List<DiaryDay>> byMonth = new HashMap<>();

    for (DiaryMovie movie : movies) {
      DiaryDay dayResponse =
          new DiaryDay(movie.getViewingDate().getDayOfMonth(), DiaryMovieType.from(movie));

      znu.visum.core.models.common.Month month = Month.from(movie.getViewingDate().getMonth());
      if (byMonth.containsKey(month)) {
        List<DiaryDay> monthMovies = byMonth.get(month);
        monthMovies.add(dayResponse);
      } else {
        byMonth.put(month, new ArrayList<>(Arrays.asList(dayResponse)));
      }
    }

    return new DiaryYear(
        yearValue,
        MONTH_VALUES.stream()
            .map(
                month -> {
                  // Sort by day
                  if (byMonth.get(month) != null) {
                    Comparator<DiaryDay> byDay = Comparator.comparing(DiaryDay::getDay);
                    byMonth.get(month).sort(byDay.reversed());
                  }

                  return new DiaryMonth(
                      month, byMonth.get(month) != null ? byMonth.get(month) : new ArrayList<>());
                })
            .collect(Collectors.toList()));
  }

  @Override
  public String toString() {
    return "DiaryYear{" + "months='" + months + "'," + "year='" + year + "'" + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryYear that = (DiaryYear) o;
    return java.util.Objects.equals(months, that.months) && year == that.year;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(months, year);
  }
}
