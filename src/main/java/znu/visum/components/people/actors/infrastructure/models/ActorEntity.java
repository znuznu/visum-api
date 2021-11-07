package znu.visum.components.people.actors.infrastructure.models;

import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.models.MovieFromActor;
import znu.visum.components.people.infrastructure.models.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "actor", uniqueConstraints = @UniqueConstraint(columnNames = {"forename", "name"}))
public class ActorEntity extends PeopleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_id_seq")
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "movie_actor",
      joinColumns = @JoinColumn(name = "actor_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<MovieEntity> movieEntities;

  public ActorEntity() {}

  public static ActorEntity from(Actor actor) {
    return new ActorEntity()
        .id(actor.getId())
        .name(actor.getName())
        .forename(actor.getForename())
        .movies(new HashSet<>(actor.getMovies()));
  }

  public static ActorEntity from(ActorFromMovie actorFromMovie) {
    return new ActorEntity()
        .id(actorFromMovie.getId())
        .name(actorFromMovie.getName())
        .forename(actorFromMovie.getForename());
  }

  public Actor toDomain() {
    return new Actor(
        this.id,
        this.name,
        this.forename,
        this.movieEntities.stream()
            .map(MovieEntity::toMovieFromActor)
            .collect(Collectors.toList()));
  }

  public ActorFromMovie toActorFromMovieDomain() {
    return new ActorFromMovie(this.id, this.name, this.forename);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ActorEntity id(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ActorEntity name(String name) {
    this.name = name;
    return this;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public ActorEntity forename(String forename) {
    this.forename = forename;
    return this;
  }

  public Set<MovieEntity> getMovies() {
    return movieEntities;
  }

  public void setMovies(Set<MovieEntity> movieEntities) {
    this.movieEntities = movieEntities;
  }

  public ActorEntity movies(Set<MovieFromActor> actorMovies) {
    this.movieEntities = actorMovies.stream().map(MovieEntity::from).collect(Collectors.toSet());
    return this;
  }
}
