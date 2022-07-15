package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.diary.domain.DiaryEntry;
import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class MovieDiaryFragment {

  private static final Comparator<ViewingEntry> BY_DATES_DESC =
      Comparator.comparing(ViewingEntry::getDate)
          .thenComparing(ViewingEntry::getCreationDate)
          .reversed();

  private final long id;
  private final String title;
  private final LocalDate releaseDate;
  private final boolean isFavorite;
  private final ViewingHistory viewingHistory;
  private final String posterUrl;
  private final Review review;

  public List<DiaryEntry> splitAsDiaryMovies() {
    if (this.viewingHistory.isEmpty()) {
      return new ArrayList<>();
    }

    var datesSortedDesc =
        this.viewingHistory.getEntries().stream()
            .filter(entry -> entry.getDate() != null)
            .sorted(BY_DATES_DESC)
            .toList();

    var lastDiaryEntry =
        this.toSeenFirstTime(datesSortedDesc.get(datesSortedDesc.size() - 1).getDate());

    // All movie that follows are rewatch
    var diaryMovies =
        datesSortedDesc.stream()
            .limit(datesSortedDesc.size() - 1)
            .map(entry -> this.toRewatch(entry.getDate()))
            .collect(Collectors.toList());

    diaryMovies.add(lastDiaryEntry);

    return diaryMovies;
  }

  private DiaryEntry toRewatch(LocalDate viewingDate) {
    var entryReview =
        this.review != null
            ? new DiaryEntry.Review(this.review.getId(), this.review.getGrade())
            : null;

    return DiaryEntry.builder()
        .movieId(id)
        .title(title)
        .releaseDate(releaseDate)
        .review(entryReview)
        .viewingDate(viewingDate)
        .isFavorite(isFavorite)
        .posterUrl(posterUrl)
        .isRewatch(true)
        .build();
  }

  private DiaryEntry toSeenFirstTime(LocalDate viewingDate) {
    var entryReview =
        this.review != null
            ? new DiaryEntry.Review(this.review.getId(), this.review.getGrade())
            : null;

    return DiaryEntry.builder()
        .movieId(id)
        .title(title)
        .releaseDate(releaseDate)
        .review(entryReview)
        .viewingDate(viewingDate)
        .isFavorite(isFavorite)
        .posterUrl(posterUrl)
        .isRewatch(false)
        .build();
  }

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
