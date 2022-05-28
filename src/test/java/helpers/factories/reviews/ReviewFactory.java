package helpers.factories.reviews;

import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.domain.models.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum ReviewFactory {
  INSTANCE;

  private Review createReview(long id, long movieId) {
    return Review.builder()
        .id(id)
        .content("Bla bla bla. \n Blo blo blo. \n Wow !")
        .grade(7)
        .movie(
            MovieFromReview.builder()
                .id(movieId)
                .title("Fake movie")
                .releaseDate(LocalDate.of(2021, 6, 12))
                .metadata(new MovieFromReview.MovieFromReviewMetadata("https://images.com/1234"))
                .build())
        .creationDate(LocalDateTime.of(2021, 12, 12, 5, 10))
        .updateDate(LocalDateTime.of(2021, 12, 12, 7, 10))
        .build();
  }

  private Review createReviewToSave(long movieId) {
    return Review.builder()
        .content("Bla bla bla. \n Blo blo blo. \n Wow !")
        .grade(7)
        .movie(MovieFromReview.builder().id(movieId).build())
        .build();
  }

  public Review getOneWithIdAndMovieId(long id, long movieId) {
    return createReview(id, movieId);
  }

  public Review getOneToSaveWithMovieId(long movieId) {
    return createReviewToSave(movieId);
  }
}
