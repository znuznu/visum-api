package znu.visum.components.movies.domain;

import lombok.*;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movie {

  private Long id;
  private String title;
  private LocalDate releaseDate;
  private Cast cast;
  private List<DirectorFromMovie> directors;
  private ReviewFromMovie review;
  private List<Genre> genres;
  private MovieMetadata metadata;
  private LocalDateTime creationDate;

  // TODO should be linked to an Account
  private boolean isFavorite;
  private boolean isToWatch;
  private ViewingHistory viewingHistory;

  public void addReview(ReviewFromMovie review) {
    if (this.review != null) {
      throw MaximumMovieReviewsReachedException.withId(this.id);
    }

    this.review = review;
  }
}
