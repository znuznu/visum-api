package znu.visum.components.diary.usecases.query.application.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.diary.domain.models.DiaryMovie;

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

  public static DiaryMovieType from(DiaryMovie movie) {
    return DiaryMovieType.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .posterUrl(movie.getPosterUrl())
        .releaseDate(movie.getReleaseDate())
        .grade(movie.getReview() != null ? movie.getReview().getGrade() : null)
        .reviewId(movie.getReview() != null ? movie.getReview().getId() : null)
        .isFavorite(movie.isFavorite())
        .isRewatch(movie.isRewatch())
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryMovieType that = (DiaryMovieType) o;
    return java.util.Objects.equals(id, that.id)
        && java.util.Objects.equals(title, that.title)
        && java.util.Objects.equals(posterUrl, that.posterUrl)
        && java.util.Objects.equals(releaseDate, that.releaseDate)
        && java.util.Objects.equals(grade, that.grade)
        && isFavorite == that.isFavorite
        && isRewatch == that.isRewatch
        && java.util.Objects.equals(reviewId, that.reviewId);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(
        id, title, posterUrl, releaseDate, grade, isFavorite, isRewatch, reviewId);
  }
}
