package znu.visum.components.people.actors.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.movies.infrastructure.MovieEntity;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.infrastructure.PeopleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "actor", uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId"}))
@Getter
@NoArgsConstructor
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

  @Embedded private ActorMetadataEntity metadataEntity;

  @Builder
  public ActorEntity(
      Long id,
      Set<MovieEntity> movieEntities,
      String name,
      String forename,
      ActorMetadataEntity metadataEntity) {
    super(name, forename);
    this.id = id;
    this.movieEntities = movieEntities;
    this.metadataEntity = metadataEntity;
  }

  public static ActorEntity from(Actor actor) {
    return ActorEntity.builder()
        .id(actor.getId())
        .name(actor.getName())
        .forename(actor.getForename())
        .movieEntities(
            new HashSet<>(
                actor.getMovies().stream().map(MovieEntity::from).collect(Collectors.toList())))
        .metadataEntity(ActorMetadataEntity.from(actor.getMetadata()))
        .build();
  }

  public static ActorEntity from(ActorFromMovie actorFromMovie) {
    return ActorEntity.builder()
        .id(actorFromMovie.getId())
        .name(actorFromMovie.getName())
        .forename(actorFromMovie.getForename())
        .metadataEntity(ActorMetadataEntity.from(actorFromMovie.getMetadata()))
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
        .metadata(this.metadataEntity.toDomain())
        .build();
  }

  public ActorFromMovie toActorFromMovieDomain() {
    return ActorFromMovie.builder()
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

    ActorEntity other = (ActorEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
