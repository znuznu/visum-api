package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Movie {

  private Long id;
  private String title;
  private LocalDate releaseDate;
  private Cast cast;
  private List<DirectorFromMovie> directors;
  private ReviewFromMovie review;
  private List<Genre> genres;
  @Builder.Default private List<ViewingHistory> viewingHistory = new ArrayList<>();
  private MovieMetadata metadata;
  private LocalDateTime creationDate;

  // TODO remove
  private boolean isFavorite;
  private boolean isToWatch;

  public void addReview(ReviewFromMovie review) {
    if (this.review != null) {
      throw MaximumMovieReviewsReachedException.withId(this.id);
    }

    this.review = review;
  }
}
