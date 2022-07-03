package znu.visum.components.reviews.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.reviews.domain.*;

import javax.transaction.Transactional;

@Service
public class CreateReview {

  private final ReviewRepository reviewRepository;
  private final MovieQueryRepository movieQueryRepository;

  @Autowired
  public CreateReview(
      ReviewRepository reviewRepository, MovieQueryRepository movieQueryRepository) {
    this.reviewRepository = reviewRepository;
    this.movieQueryRepository = movieQueryRepository;
  }

  @Transactional
  public Review process(CreateReviewCommand command) {
    var movie =
        movieQueryRepository
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
