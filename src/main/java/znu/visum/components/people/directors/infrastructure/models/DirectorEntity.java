package znu.visum.components.people.directors.infrastructure.models;

import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;
import znu.visum.components.people.infrastructure.models.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "director", uniqueConstraints = @UniqueConstraint(columnNames = {"forename", "name"}))
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

  public static DirectorEntity from(Director director) {
    return new DirectorEntity()
        .id(director.getId())
        .name(director.getName())
        .forename(director.getForename())
        .movies(new HashSet<>(director.getMovies()));
  }

  public static DirectorEntity fromDirectorFromMovie(DirectorFromMovie directorFromMovie) {
    return new DirectorEntity()
        .id(directorFromMovie.getId())
        .name(directorFromMovie.getName())
        .forename(directorFromMovie.getForename());
  }

  public Director toDomain() {
    return new Director(
        this.id,
        this.name,
        this.forename,
        this.movieEntities.stream()
            .map(MovieEntity::toMovieFromDirector)
            .collect(Collectors.toList()));
  }

  public DirectorFromMovie toDirectorFromMovieDomain() {
    return new DirectorFromMovie(this.id, this.name, this.forename);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DirectorEntity id(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DirectorEntity name(String name) {
    this.name = name;
    return this;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public DirectorEntity forename(String forename) {
    this.forename = forename;
    return this;
  }

  public Set<MovieEntity> getMovies() {
    return movieEntities;
  }

  public void setMovies(Set<MovieEntity> movieEntities) {
    this.movieEntities = movieEntities;
  }

  public DirectorEntity movies(Set<MovieFromDirector> directorMovies) {
    this.movieEntities = directorMovies.stream().map(MovieEntity::from).collect(Collectors.toSet());
    return this;
  }
}
