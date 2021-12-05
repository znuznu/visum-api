package znu.visum.components.movies.infrastructure.models;

import org.hibernate.annotations.CreationTimestamp;
import znu.visum.components.genres.infrastructure.models.GenreEntity;
import znu.visum.components.history.infrastructure.models.MovieViewingHistoryEntity;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.ReviewFromMovie;
import znu.visum.components.people.actors.domain.models.MovieFromActor;
import znu.visum.components.people.actors.infrastructure.models.ActorEntity;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;
import znu.visum.components.people.directors.infrastructure.models.DirectorEntity;
import znu.visum.components.reviews.domain.models.MovieFromReview;
import znu.visum.components.reviews.infrastructure.models.MovieReviewEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
    name = "movie",
    uniqueConstraints = @UniqueConstraint(columnNames = {"title", "releaseDate"}))
public class MovieEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
  private Long id;

  private String title;

  private LocalDate releaseDate;

  @ManyToMany
  @JoinTable(
      name = "movie_actor",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "actor_id"))
  private Set<ActorEntity> actorEntities;

  @ManyToMany
  @JoinTable(
      name = "movie_director",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "director_id"))
  private Set<DirectorEntity> directorEntities;

  @OneToOne(mappedBy = "movieEntity", cascade = CascadeType.REMOVE)
  private MovieReviewEntity review;

  @ManyToMany
  @JoinTable(
      name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<GenreEntity> genreEntities;

  private boolean isFavorite;

  private boolean shouldWatch;

  @CreationTimestamp private LocalDateTime creationDate;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MovieViewingHistoryEntity> viewingHistory = new ArrayList<>();

  @OneToOne(
      mappedBy = "movie",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @PrimaryKeyJoinColumn
  private MovieMetadataEntity movieMetadataEntity;

  public MovieEntity() {}

  public MovieEntity(MovieEntity movieEntity) {
    this.id = movieEntity.id;
    this.title = movieEntity.title;
    this.actorEntities = movieEntity.actorEntities;
    this.directorEntities = movieEntity.directorEntities;
    this.genreEntities = movieEntity.genreEntities;
    this.review = movieEntity.review;
    this.releaseDate = movieEntity.releaseDate;
    this.creationDate = movieEntity.creationDate;
    this.shouldWatch = movieEntity.shouldWatch;
    this.isFavorite = movieEntity.isFavorite;
    this.viewingHistory = movieEntity.viewingHistory;
    this.movieMetadataEntity = movieEntity.movieMetadataEntity;
  }

  public static MovieEntity from(MovieFromReview movieFromReview) {
    return new MovieEntity.Builder()
        .id(movieFromReview.getId())
        .title(movieFromReview.getTitle())
        .releaseDate(movieFromReview.getReleaseDate())
        .build();
  }

  public static MovieEntity from(Movie movie) {
    return new MovieEntity.Builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .releaseDate(movie.getReleaseDate())
        .creationDate(movie.getCreationDate())
        .actors(movie.getActors().stream().map(ActorEntity::from).collect(Collectors.toSet()))
        .directors(
            movie.getDirectors().stream()
                .map(DirectorEntity::fromDirectorFromMovie)
                .collect(Collectors.toSet()))
        .isFavorite(movie.isFavorite())
        .shouldWatch(movie.isToWatch())
        .review(movie.getReview() == null ? null : MovieReviewEntity.from(movie.getReview()))
        .genreEntities(
            movie.getGenres().stream().map(GenreEntity::from).collect(Collectors.toSet()))
        .viewingDates(
            movie.getViewingHistory().stream()
                .map(MovieViewingHistoryEntity::from)
                .collect(Collectors.toList()))
        .metadata(null) // We wan't to override this property anyway
        .build();
  }

  public static MovieEntity from(MovieFromActor movieFromActor) {
    return new MovieEntity.Builder()
        .id(movieFromActor.getId())
        .title(movieFromActor.getTitle())
        .releaseDate(movieFromActor.getReleaseDate())
        .build();
  }

  public static MovieEntity from(MovieFromDirector movieFromDirector) {
    return new MovieEntity.Builder()
        .id(movieFromDirector.getId())
        .title(movieFromDirector.getTitle())
        .releaseDate(movieFromDirector.getReleaseDate())
        .build();
  }

  public Movie toDomain() {
    return new Movie.Builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .actors(
            this.actorEntities.stream()
                .map(ActorEntity::toActorFromMovieDomain)
                .collect(Collectors.toList()))
        .directors(
            this.directorEntities.stream()
                .map(DirectorEntity::toDirectorFromMovieDomain)
                .collect(Collectors.toList()))
        .genres(this.genreEntities.stream().map(GenreEntity::toDomain).collect(Collectors.toList()))
        .review(this.review == null ? null : ReviewFromMovie.from(this.review.toDomain()))
        .toWatch(this.shouldWatch)
        .favorite(this.isFavorite)
        .creationDate(this.creationDate)
        .viewingDates(
            this.viewingHistory.stream()
                .map(MovieViewingHistoryEntity::toDomain)
                .collect(Collectors.toList()))
        .metadata(this.movieMetadataEntity == null ? null : this.movieMetadataEntity.toDomain())
        .build();
  }

  public MovieFromReview toMovieFromReview() {
    return new MovieFromReview(
        this.id,
        this.title,
        this.releaseDate,
        this.movieMetadataEntity == null ? null : new MovieFromReview.MovieFromReviewMetadata(this.movieMetadataEntity.getPosterUrl()));
  }

  public MovieFromActor toMovieFromActor() {
    return new MovieFromActor(
        this.id, this.title, this.releaseDate, this.isFavorite, this.shouldWatch);
  }

  public MovieFromDirector toMovieFromDirector() {
    return new MovieFromDirector(
        this.id, this.title, this.releaseDate, this.isFavorite, this.shouldWatch);
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

  public Set<ActorEntity> getActors() {
    return actorEntities;
  }

  public void setActors(Set<ActorEntity> actorEntities) {
    this.actorEntities = actorEntities;
  }

  public Set<DirectorEntity> getDirectors() {
    return directorEntities;
  }

  public void setDirectors(Set<DirectorEntity> directorEntities) {
    this.directorEntities = directorEntities;
  }

  public Set<GenreEntity> getGenres() {
    return genreEntities;
  }

  public void setGenres(Set<GenreEntity> genreEntities) {
    this.genreEntities = genreEntities;
  }

  public MovieReviewEntity getReview() {
    return review;
  }

  public void setReview(MovieReviewEntity review) {
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

  public List<MovieViewingHistoryEntity> getViewingHistory() {
    return viewingHistory;
  }

  public void setViewingHistory(List<MovieViewingHistoryEntity> viewingHistory) {
    this.viewingHistory = viewingHistory;
  }

  public void setMovieMetadata(MovieMetadataEntity movieMetadataEntity) {
    this.movieMetadataEntity = movieMetadataEntity;
  }

  public MovieMetadataEntity getMovieMetadataEntity() {
    return movieMetadataEntity;
  }

  public static final class Builder {
    private final MovieEntity movieEntity;

    public Builder() {
      movieEntity = new MovieEntity();
    }

    public MovieEntity.Builder id(Long id) {
      movieEntity.setId(id);
      return this;
    }

    public MovieEntity.Builder title(String title) {
      movieEntity.setTitle(title);
      return this;
    }

    public MovieEntity.Builder actors(Set<ActorEntity> actorEntities) {
      movieEntity.setActors(actorEntities);
      return this;
    }

    public MovieEntity.Builder directors(Set<DirectorEntity> directorEntities) {
      movieEntity.setDirectors(directorEntities);
      return this;
    }

    public MovieEntity.Builder genreEntities(Set<GenreEntity> genreEntities) {
      movieEntity.setGenres(genreEntities);
      return this;
    }

    public MovieEntity.Builder review(MovieReviewEntity review) {
      movieEntity.setReview(review);
      return this;
    }

    public MovieEntity.Builder releaseDate(LocalDate releaseDate) {
      movieEntity.setReleaseDate(releaseDate);
      return this;
    }

    public MovieEntity.Builder creationDate(LocalDateTime creationDate) {
      movieEntity.setCreationDate(creationDate);
      return this;
    }

    public MovieEntity.Builder shouldWatch(boolean shouldWatch) {
      movieEntity.setShouldWatch(shouldWatch);
      return this;
    }

    public MovieEntity.Builder isFavorite(boolean isFavorite) {
      movieEntity.setFavorite(isFavorite);
      return this;
    }

    public MovieEntity.Builder viewingDates(List<MovieViewingHistoryEntity> viewingHistory) {
      movieEntity.setViewingHistory(viewingHistory);
      return this;
    }

    public MovieEntity.Builder metadata(MovieMetadataEntity metadata) {
      movieEntity.setMovieMetadata(metadata);
      return this;
    }

    public MovieEntity build() {
      return new MovieEntity(movieEntity);
    }
  }
}
