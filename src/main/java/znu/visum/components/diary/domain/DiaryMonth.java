package znu.visum.components.diary.domain;

import lombok.Getter;
import znu.visum.core.assertions.VisumAssert;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DiaryMonth {

  private static final Comparator<DiaryEntry> BY_REWATCH =
      (de1, de2) -> {
        if (de1.isRewatch() == de2.isRewatch()) {
          return 0;
        }

        if (de1.isRewatch()) {
          return 1;
        }

        return -1;
      };

  private static final Comparator<DiaryDay> BY_DAY_DESC =
      Comparator.comparingInt(DiaryDay::getDay)
          .thenComparing(DiaryDay::getMovie, BY_REWATCH)
          .reversed();

  @Getter private final Month month;
  private final Collection<DiaryDay> days;

  private DiaryMonth(Month month, Collection<DiaryDay> days) {
    VisumAssert.notNull("month", month);

    this.month = month;
    this.days = days;
  }

  public static DiaryMonth of(Month month) {
    return new DiaryMonth(month, new ArrayList<>());
  }

  public Collection<DiaryDay> getDaysInDescendingOrder() {
    return days.stream().sorted(BY_DAY_DESC).collect(Collectors.toUnmodifiableList());
  }

  public void addAll(DiaryEntry... entries) {
    Arrays.stream(entries).forEach(this::add);
  }

  private void add(DiaryEntry entry) {
    if (entry.getViewingDate().getMonth() != this.month) {
      throw InvalidDiaryMovieEntryDate.of(this.month, entry.getViewingDate());
    }

    this.days.add(DiaryDay.of(entry));
  }
}
