package znu.visum.components.reviews.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.reviews.domain.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.Review;
import znu.visum.components.reviews.domain.ReviewRepository;

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
