package znu.visum.business.reviews.movies.models;

import com.fasterxml.jackson.annotation.JsonView;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.reviews.Review;
import znu.visum.core.pagination.PageWrapper;

import javax.persistence.*;

@Entity
public class MovieReview extends Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_review_id_seq")
    @JsonView({
            Views.Id.class,
            Movie.Views.Light.class,
            PageWrapper.Views.Full.class,
            PageWrapper.Views.Reviews.class
    })
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    @JsonView({
            Views.Light.class,
            PageWrapper.Views.Reviews.class
    })
    private Movie movie;

    public MovieReview() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
