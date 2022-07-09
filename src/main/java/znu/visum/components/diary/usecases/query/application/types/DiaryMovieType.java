package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.diary.domain.DiaryEntry;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class DiaryMovieType {

  private long id;
  private String title;
  private String posterUrl;
  private LocalDate releaseDate;
  private Integer grade;
  private boolean isFavorite;
  private boolean isRewatch;
  private Long reviewId;

  public static DiaryMovieType from(DiaryEntry movie) {
    return DiaryMovieType.builder()
        .id(movie.getMovieId())
        .title(movie.getTitle())
        .posterUrl(movie.getPosterUrl())
        .releaseDate(movie.getReleaseDate())
        .grade(movie.getReview() != null ? movie.getReview().getGrade().getValue() : null)
        .reviewId(movie.getReview() != null ? movie.getReview().getId() : null)
        .isFavorite(movie.isFavorite())
        .isRewatch(movie.isRewatch())
        .build();
  }
}
