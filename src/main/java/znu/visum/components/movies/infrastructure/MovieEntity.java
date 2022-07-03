package znu.visum.components.movies.infrastructure;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import znu.visum.components.genres.infrastructure.GenreEntity;
import znu.visum.components.history.infrastructure.MovieViewingHistoryEntity;
import znu.visum.components.movies.domain.Cast;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.person.actors.domain.MovieFromActor;
import znu.visum.components.person.directors.domain.MovieFromDirector;
import znu.visum.components.person.directors.infrastructure.DirectorEntity;
import znu.visum.components.reviews.domain.MovieFromReview;
import znu.visum.components.reviews.infrastructure.MovieReviewEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
  private Long id;

  private String title;

  private LocalDate releaseDate;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CastMemberEntity> castMembersEntity = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "movie_director",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "director_id"))
  private Set<DirectorEntity> directorEntities;

  @OneToOne(mappedBy = "movieEntity", cascade = CascadeType.REMOVE)
  private MovieReviewEntity review;

  @ManyToMany(cascade = {CascadeType.PERSIST})
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
        .castMembersEntity(null)
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
        .cast(
            this.castMembersEntity != null
                ? Cast.of(
                    this.castMembersEntity.stream()
                        .map(CastMemberEntity::toDomain)
                        .collect(Collectors.toList()))
                : Cast.of(List.of()))
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

  public void setCastMembers(List<CastMemberEntity> members) {
    this.castMembersEntity = members;
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
