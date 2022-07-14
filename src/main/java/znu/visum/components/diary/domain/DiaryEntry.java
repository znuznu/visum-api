package znu.visum.components.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDate;
import java.time.Year;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class DiaryEntry {

  private final long movieId;
  private final String title;
  private final LocalDate releaseDate;
  private final Review review;
  private final boolean isFavorite;
  private final boolean isRewatch;
  private final LocalDate viewingDate;
  private final String posterUrl;

  public boolean isOfYear(Year year) {
    return this.getViewingDate().getYear() == year.getValue();
  }

  @EqualsAndHashCode
  @Getter
  public static class Review {

    private final long id;
    private final Grade grade;

    public Review(long id, Grade grade) {
      VisumAssert.notNull("grade", grade);

      this.id = id;
      this.grade = grade;
    }
  }
}
