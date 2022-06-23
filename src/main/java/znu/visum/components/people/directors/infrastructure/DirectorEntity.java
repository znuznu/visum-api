package znu.visum.components.people.directors.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.movies.infrastructure.MovieEntity;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.infrastructure.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "director", uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId"}))
@NoArgsConstructor
@Getter
public class DirectorEntity extends PeopleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_id_seq")
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "movie_director",
      joinColumns = @JoinColumn(name = "director_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<MovieEntity> movieEntities;

  @Embedded private DirectorMetadataEntity metadataEntity;

  @Builder
  public DirectorEntity(
      Long id,
      Set<MovieEntity> movieEntities,
      String name,
      String forename,
      DirectorMetadataEntity metadataEntity) {
    super(name, forename);
    this.id = id;
    this.movieEntities = movieEntities;
    this.metadataEntity = metadataEntity;
  }

  public static DirectorEntity from(Director director) {
    return DirectorEntity.builder()
        .id(director.getId())
        .name(director.getName())
        .forename(director.getForename())
        .movieEntities(
            new HashSet<>(
                director.getMovies().stream().map(MovieEntity::from).collect(Collectors.toSet())))
        .metadataEntity(DirectorMetadataEntity.from(director.getMetadata()))
        .build();
  }

  public static DirectorEntity from(DirectorFromMovie directorFromMovie) {
    return DirectorEntity.builder()
        .id(directorFromMovie.getId())
        .name(directorFromMovie.getName())
        .forename(directorFromMovie.getForename())
        .metadataEntity(DirectorMetadataEntity.from(directorFromMovie.getMetadata()))
        .build();
  }

  public Director toDomain() {
    return Director.builder()
        .id(this.id)
        .name(this.name)
        .forename(this.forename)
        .movies(
            this.movieEntities.stream()
                .map(MovieEntity::toMovieFromDirector)
                .collect(Collectors.toList()))
        .metadata(this.metadataEntity.toDomain())
        .build();
  }

  public DirectorFromMovie toDirectorFromMovieDomain() {
    return DirectorFromMovie.builder()
        .id(this.id)
        .name(this.name)
        .forename(this.forename)
        .metadata(this.metadataEntity.toDomain())
        .build();
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

    DirectorEntity other = (DirectorEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
