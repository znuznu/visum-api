package znu.visum.business.history.movies.models;

import com.fasterxml.jackson.annotation.JsonView;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.history.models.ViewingHistory;

import javax.persistence.*;

@Entity
public class MovieViewingHistory extends ViewingHistory {
    @JsonView({
            Views.Id.class,
            Movie.Views.Full.class
    })
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_viewing_history_id_seq")
    private Long id;

    @JsonView(Views.Full.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    public MovieViewingHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
