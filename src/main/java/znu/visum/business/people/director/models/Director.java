package znu.visum.business.people.director.models;

import com.fasterxml.jackson.annotation.JsonView;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.people.People;
import znu.visum.core.pagination.PageWrapper;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"forename", "name"}))
public class Director extends People {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_id_seq")
    @JsonView({
            Views.Id.class,
            Movie.Views.Light.class,
            PageWrapper.Views.Full.class
    })
    private Long id;

    @JsonView(Views.Full.class)
    @ManyToMany
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "director_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies;

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
