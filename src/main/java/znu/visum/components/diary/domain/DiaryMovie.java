package znu.visum.components.diary.domain;

import lombok.*;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.core.exceptions.domain.InternalException;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DiaryMovie {

  private long id;
  private String title;
  private LocalDate releaseDate;
  private ReviewFromMovie review;
  private boolean isFavorite;
  private boolean isRewatch;
  private LocalDate viewingDate;
  private String posterUrl;

  public static DiaryMovie from(Movie movie, long viewingHistoryId) {
    LocalDate viewingDate =
        movie.getViewingHistory().stream()
            .filter(history -> history.getId() == viewingHistoryId)
            .map(MovieViewingHistory::getViewingDate)
            .findFirst()
            .orElseThrow(
                () ->
                    InternalException.withMessage(
                        "Viewing history ID not found in the movie viewing dates."));
    boolean isRewatch =
        movie.getViewingHistory().stream()
            .anyMatch(
                history ->
                    history.getViewingDate() != null
                        && history.getViewingDate().isBefore(viewingDate));

    return DiaryMovie.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .releaseDate(movie.getReleaseDate())
        .review(movie.getReview())
        .viewingDate(viewingDate)
        .isFavorite(movie.isFavorite())
        .isRewatch(isRewatch)
        .posterUrl(movie.getMetadata() != null ? movie.getMetadata().getPosterUrl() : null)
        .build();
  }
}
