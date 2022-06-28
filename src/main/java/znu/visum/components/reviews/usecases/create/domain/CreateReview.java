package znu.visum.components.reviews.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.reviews.domain.*;

import javax.transaction.Transactional;

@Service
public class CreateReview {

  private final ReviewRepository reviewRepository;
  private final MovieRepository movieRepository;

  @Autowired
  public CreateReview(ReviewRepository reviewRepository, MovieRepository movieRepository) {
    this.reviewRepository = reviewRepository;
    this.movieRepository = movieRepository;
  }

  @Transactional
  public Review process(CreateReviewCommand command) {
    var movie =
        movieRepository
            .findById(command.getMovieId())
            .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(command.getMovieId())));
    if (movie.getReview() != null) {
      // TODO should be done using movie.addReview()
      throw MaximumMovieReviewsReachedException.withId(command.getMovieId());
    }

    var review =
        Review.builder()
            .movie(MovieFromReview.builder().id(command.getMovieId()).build())
            .grade(Grade.of(command.getGrade()))
            .content(Content.of(command.getContent()))
            .build();

    return reviewRepository.save(review);
  }
}
