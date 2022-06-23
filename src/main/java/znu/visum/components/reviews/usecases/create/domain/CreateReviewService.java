package znu.visum.components.reviews.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.reviews.domain.errors.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.models.Review;
import znu.visum.components.reviews.domain.ports.ReviewRepository;

import javax.transaction.Transactional;

@Service
public class CreateReviewService {
  private final ReviewRepository reviewRepository;

  private final MovieRepository movieRepository;

  @Autowired
  public CreateReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
    this.reviewRepository = reviewRepository;
    this.movieRepository = movieRepository;
  }

  @Transactional
  public Review save(Review review) {
    long movieId = review.getMovie().getId();
    Movie movie =
        movieRepository
            .findById(movieId)
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

    if (movie.getReview() != null) {
      throw MaximumMovieReviewsReachedException.withId(movieId);
    }

    return reviewRepository.save(review);
  }
}
