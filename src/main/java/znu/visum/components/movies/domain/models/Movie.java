package znu.visum.components.movies.domain.models;

import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.history.domain.models.MovieViewingHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Movie {
  private Long id;

  private String title;

  private LocalDate releaseDate;

  private List<ActorFromMovie> actors;

  private List<DirectorFromMovie> directors;

  private ReviewFromMovie review;

  private List<Genre> genres;

  private boolean favorite;

  private boolean toWatch;

  private LocalDateTime creationDate;

  private List<MovieViewingHistory> viewingHistory = new ArrayList<>();

  private MovieMetadata metadata;

  public Movie() {}

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

  public List<ActorFromMovie> getActors() {
    return actors;
  }

  public void setActors(List<ActorFromMovie> actors) {
    this.actors = actors;
  }

  public List<DirectorFromMovie> getDirectors() {
    return directors;
  }

  public void setDirectors(List<DirectorFromMovie> directors) {
    this.directors = directors;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public ReviewFromMovie getReview() {
    return review;
  }

  public void setReview(ReviewFromMovie review) {
    this.review = review;
  }

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public boolean isToWatch() {
    return toWatch;
  }

  public void setToWatch(boolean toWatch) {
    this.toWatch = toWatch;
  }

  public List<MovieViewingHistory> getViewingHistory() {
    return viewingHistory;
  }

  public void setViewingHistory(List<MovieViewingHistory> viewingHistory) {
    this.viewingHistory = viewingHistory;
  }

  public MovieMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(MovieMetadata metadata) {
    this.metadata = metadata;
  }

  public static final class Builder {
    private final Movie movie;

    public Builder() {
      movie = new Movie();
    }

    public Builder id(Long id) {
      movie.setId(id);
      return this;
    }

    public Movie.Builder title(String title) {
      movie.setTitle(title);
      return this;
    }

    public Builder actors(List<ActorFromMovie> actors) {
      movie.setActors(actors);
      return this;
    }

    public Builder directors(List<DirectorFromMovie> directors) {
      movie.setDirectors(directors);
      return this;
    }

    public Builder genres(List<Genre> genres) {
      movie.setGenres(genres);
      return this;
    }

    public Builder favorite(boolean favorite) {
      movie.setFavorite(favorite);
      return this;
    }

    public Builder review(ReviewFromMovie review) {
      movie.setReview(review);
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      movie.setReleaseDate(releaseDate);
      return this;
    }

    public Builder creationDate(LocalDateTime creationDate) {
      movie.setCreationDate(creationDate);
      return this;
    }

    public Builder toWatch(boolean toWatch) {
      movie.setToWatch(toWatch);
      return this;
    }

    public Builder viewingDates(List<MovieViewingHistory> viewingHistory) {
      movie.setViewingHistory(viewingHistory);
      return this;
    }

    public Builder metadata(MovieMetadata metadata) {
      movie.setMetadata(metadata);
      return this;
    }

    public Movie build() {
      return this.movie;
    }
  }
}
