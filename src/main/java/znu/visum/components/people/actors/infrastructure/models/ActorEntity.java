package znu.visum.components.people.actors.infrastructure.models;

import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.infrastructure.models.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "actor", uniqueConstraints = @UniqueConstraint(columnNames = {"forename", "name"}))
@Getter
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

  @Builder
  public ActorEntity(Long id, Set<MovieEntity> movieEntities, String name, String forename) {
    super(name, forename);
    this.id = id;
    this.movieEntities = movieEntities;
  }

  public static ActorEntity from(Actor actor) {
    return ActorEntity.builder()
        .id(actor.getId())
        .name(actor.getName())
        .forename(actor.getForename())
        .movieEntities(
            new HashSet<>(
                actor.getMovies().stream().map(MovieEntity::from).collect(Collectors.toList())))
        .build();
  }

  public static ActorEntity from(ActorFromMovie actorFromMovie) {
    return ActorEntity.builder()
        .id(actorFromMovie.getId())
        .name(actorFromMovie.getName())
        .forename(actorFromMovie.getForename())
        .build();
  }

  public Actor toDomain() {
    return Actor.builder()
        .id(this.id)
        .name(this.name)
        .forename(this.forename)
        .movies(
            this.movieEntities.stream()
                .map(MovieEntity::toMovieFromActor)
                .collect(Collectors.toList()))
        .build();
  }

  public ActorFromMovie toActorFromMovieDomain() {
    return ActorFromMovie.builder().id(this.id).name(this.name).forename(this.forename).build();
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

    ActorEntity other = (ActorEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
