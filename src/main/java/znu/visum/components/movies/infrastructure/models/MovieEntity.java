package znu.visum.components.movies.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "movie")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
  private List<MovieViewingHistoryEntity> viewingHistory;

  @OneToOne(
      mappedBy = "movie",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @PrimaryKeyJoinColumn
  private MovieMetadataEntity movieMetadataEntity;

  public static MovieEntity from(MovieFromReview movieFromReview) {
    return MovieEntity.builder()
        .id(movieFromReview.getId())
        .title(movieFromReview.getTitle())
        .releaseDate(movieFromReview.getReleaseDate())
        .build();
  }

  public static MovieEntity from(Movie movie) {
    return MovieEntity.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .releaseDate(movie.getReleaseDate())
        .creationDate(movie.getCreationDate())
        // HERE
        .actorEntities(
            movie.getActors().stream().map(ActorEntity::from).collect(Collectors.toSet()))
        .directorEntities(
            movie.getDirectors().stream().map(DirectorEntity::from).collect(Collectors.toSet()))
        .isFavorite(movie.isFavorite())
        .shouldWatch(movie.isToWatch())
        .review(movie.getReview() == null ? null : MovieReviewEntity.from(movie.getReview()))
        .genreEntities(
            movie.getGenres().stream().map(GenreEntity::from).collect(Collectors.toSet()))
        .viewingHistory(
            movie.getViewingHistory().stream()
                .map(MovieViewingHistoryEntity::from)
                .collect(Collectors.toList()))
        .movieMetadataEntity(null) // We want to override this property anyway
        .build();
  }

  public static MovieEntity from(MovieFromActor movieFromActor) {
    return MovieEntity.builder()
        .id(movieFromActor.getId())
        .title(movieFromActor.getTitle())
        .releaseDate(movieFromActor.getReleaseDate())
        .build();
  }

  public static MovieEntity from(MovieFromDirector movieFromDirector) {
    return MovieEntity.builder()
        .id(movieFromDirector.getId())
        .title(movieFromDirector.getTitle())
        .releaseDate(movieFromDirector.getReleaseDate())
        .build();
  }

  public Movie toDomain() {
    return Movie.builder()
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
        .isToWatch(this.shouldWatch)
        .isFavorite(this.isFavorite)
        .creationDate(this.creationDate)
        .viewingHistory(
            this.viewingHistory.stream()
                .map(MovieViewingHistoryEntity::toDomain)
                .collect(Collectors.toList()))
        .metadata(this.movieMetadataEntity == null ? null : this.movieMetadataEntity.toDomain())
        .build();
  }

  public MovieFromReview toMovieFromReview() {
    return MovieFromReview.builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .metadata(
            this.movieMetadataEntity == null
                ? null
                : new MovieFromReview.MovieFromReviewMetadata(
                    this.movieMetadataEntity.getPosterUrl()))
        .build();
  }

  public MovieFromActor toMovieFromActor() {
    return new MovieFromActor(
        this.id, this.title, this.releaseDate, this.isFavorite, this.shouldWatch);
  }

  public MovieFromDirector toMovieFromDirector() {
    return new MovieFromDirector(
        this.id, this.title, this.releaseDate, this.isFavorite, this.shouldWatch);
  }

  public void setMovieMetadata(MovieMetadataEntity metadata) {
    this.movieMetadataEntity = metadata;
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    MovieEntity other = (MovieEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
