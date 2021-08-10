package znu.visum.business.categories.movies.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import znu.visum.business.genres.models.Genre;
import znu.visum.business.history.movies.models.MovieViewingHistory;
import znu.visum.business.people.People;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.business.people.director.models.Director;
import znu.visum.business.reviews.Review;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.core.pagination.PageWrapper;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "releaseDate"}))
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
    @JsonView({
            Views.Id.class,
            People.Views.Full.class,
            Review.Views.Light.class,
            PageWrapper.Views.Light.class,
            MovieViewingHistory.Views.Full.class
    })
    private Long id;

    @JsonView({
            Views.Light.class,
            People.Views.Light.class,
            Review.Views.Light.class,
            PageWrapper.Views.Light.class,
    })
    private String title;

    @JsonView({
            Views.Light.class,
            People.Views.Light.class,
            Review.Views.Full.class,
            PageWrapper.Views.Full.class,
            PageWrapper.Views.Reviews.class,
    })
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;

    @JsonView({
            Views.Full.class,
            PageWrapper.Views.Full.class,
    })
    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;

    @JsonView({
            Views.Full.class,
            PageWrapper.Views.Full.class,
    })
    @ManyToMany
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors;

    @JsonView({
            Views.Light.class,
            PageWrapper.Views.Full.class
    })
    @OneToOne(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private MovieReview review;

    @JsonView({
            Views.Full.class,
            PageWrapper.Views.Full.class
    })
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @JsonView({
            Views.Light.class,
            People.Views.Light.class,
            PageWrapper.Views.Full.class,
    })
    private boolean isFavorite;

    @JsonView({
            Views.Light.class,
            People.Views.Light.class,
            PageWrapper.Views.Full.class
    })
    private boolean shouldWatch;

    @JsonView({
            Views.Full.class,
    })
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @JsonView({
            Views.Full.class,
    })
    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieViewingHistory> viewingHistory = new ArrayList<>();

    public Movie() {
    }

    public Movie(Movie movie) {
        this.id = movie.id;
        this.title = movie.title;
        this.actors = movie.actors;
        this.directors = movie.directors;
        this.genres = movie.genres;
        this.isFavorite = movie.isFavorite;
        this.review = movie.review;
        this.releaseDate = movie.releaseDate;
        this.creationDate = movie.creationDate;
        this.shouldWatch = movie.shouldWatch;
        this.viewingHistory = movie.viewingHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public MovieReview getReview() {
        return review;
    }

    public void setReview(MovieReview review) {
        this.review = review;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isShouldWatch() {
        return shouldWatch;
    }

    public void setShouldWatch(boolean shouldWatch) {
        this.shouldWatch = shouldWatch;
    }

    public List<MovieViewingHistory> getViewingHistory() {
        return viewingHistory;
    }

    public void setViewingHistory(List<MovieViewingHistory> viewingHistory) {
        this.viewingHistory = viewingHistory;
    }

    public static final class Builder {
        private final Movie movie;

        public Builder() {
            movie = new Movie();
        }

        public Movie.Builder id(Long id) {
            movie.setId(id);
            return this;
        }

        public Movie.Builder title(String title) {
            movie.setTitle(title);
            return this;
        }

        public Movie.Builder actors(Set<Actor> actors) {
            movie.setActors(actors);
            return this;
        }

        public Movie.Builder directors(Set<Director> directors) {
            movie.setDirectors(directors);
            return this;
        }

        public Movie.Builder genres(Set<Genre> genres) {
            movie.setGenres(genres);
            return this;
        }

        public Movie.Builder isFavorite(boolean isFavorite) {
            movie.setFavorite(isFavorite);
            return this;
        }

        public Movie.Builder review(MovieReview review) {
            movie.setReview(review);
            return this;
        }

        public Movie.Builder releaseDate(LocalDate releaseDate) {
            movie.setReleaseDate(releaseDate);
            return this;
        }

        public Movie.Builder creationDate(LocalDateTime creationDate) {
            movie.setCreationDate(creationDate);
            return this;
        }

        public Movie.Builder shouldWatch(boolean shouldWatch) {
            movie.setShouldWatch(shouldWatch);
            return this;
        }

        public Movie.Builder viewingDates(List<MovieViewingHistory> viewingHistory) {
            movie.setViewingHistory(viewingHistory);
            return this;
        }

        public Movie build() {
            return new Movie(movie);
        }
    }

    public static class Views {
        public interface Id {
        }

        public interface Light extends Id {
        }

        public interface Full extends Light {
        }
    }
}
