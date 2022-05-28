package znu.visum.components.people.directors.infrastructure.models;

import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.infrastructure.models.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "director", uniqueConstraints = @UniqueConstraint(columnNames = {"forename", "name"}))
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

  public DirectorEntity() {}

  @Builder
  public DirectorEntity(Long id, Set<MovieEntity> movieEntities, String name, String forename) {
    super(name, forename);
    this.id = id;
    this.movieEntities = movieEntities;
  }

  public static DirectorEntity from(Director director) {
    return DirectorEntity.builder()
        .id(director.getId())
        .name(director.getName())
        .forename(director.getForename())
        .movieEntities(
            new HashSet<>(
                director.getMovies().stream().map(MovieEntity::from).collect(Collectors.toSet())))
        .build();
  }

  public static DirectorEntity fromDirectorFromMovie(DirectorFromMovie directorFromMovie) {
    return DirectorEntity.builder()
        .id(directorFromMovie.getId())
        .name(directorFromMovie.getName())
        .forename(directorFromMovie.getForename())
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
        .build();
  }

  public DirectorFromMovie toDirectorFromMovieDomain() {
    return DirectorFromMovie.builder().id(this.id).name(this.name).forename(this.forename).build();
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
