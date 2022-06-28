package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.MovieViewingHistory;
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
  private List<ActorFromMovie> actors;
  private List<DirectorFromMovie> directors;
  private ReviewFromMovie review;
  private List<Genre> genres;
  private boolean isFavorite;
  private boolean isToWatch;
  private LocalDateTime creationDate;
  @Builder.Default private List<MovieViewingHistory> viewingHistory = new ArrayList<>();
  private MovieMetadata metadata;

  public void addReview(ReviewFromMovie review) {
    if (this.review != null) {
      throw MaximumMovieReviewsReachedException.withId(this.id);
    }

    this.review = review;
  }
}
