package znu.visum.components.person.actors.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import znu.visum.components.externals.domain.models.ExternalCastMember;
import znu.visum.components.movies.infrastructure.CastMemberEntity;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.MovieFromActor;
import znu.visum.components.person.actors.usecases.getpage.domain.PageActor;
import znu.visum.components.person.domain.Identity;
import znu.visum.components.person.infrastructure.PeopleEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "actor", uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId"}))
@Getter
@Setter
@NoArgsConstructor
public class ActorEntity extends PeopleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_id_seq")
  private Long id;

  @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CastMemberEntity> members = new ArrayList<>();

  @Embedded private ActorMetadataEntity metadataEntity;

  @Builder
  public ActorEntity(
      Long id,
      List<CastMemberEntity> members,
      String name,
      String forename,
      ActorMetadataEntity metadataEntity) {
    super(name, forename);
    this.id = id;
    this.members = members;
    this.metadataEntity = metadataEntity;
  }

  public static ActorEntity from(Actor actor) {
    return ActorEntity.builder()
        .id(actor.getId())
        .name(actor.getIdentity().getName())
        .forename(actor.getIdentity().getForename())
        .metadataEntity(ActorMetadataEntity.from(actor.getMetadata()))
        // The movie is the owner of the cast
        .members(new ArrayList<>())
        .build();
  }

  public static ActorEntity from(ExternalCastMember member) {
    return ActorEntity.builder()
        .id(null)
        .metadataEntity(
            ActorMetadataEntity.builder()
                .tmdbId(member.getId())
                .posterUrl(member.getPosterUrl())
                .build())
        .name(member.getIdentity().getName())
        .forename(member.getIdentity().getForename())
        .build();
  }

  public PageActor toPageActor() {
    return PageActor.builder()
        .id(this.id)
        .identity(Identity.builder().forename(this.getForename()).name(this.getName()).build())
        .posterUrl(this.getMetadataEntity().getPosterUrl())
        .tmdbId(this.getMetadataEntity().getTmdbId())
        .build();
  }

  public Actor toDomain() {
    return Actor.builder()
        .id(this.id)
        .identity(Identity.builder().forename(this.getForename()).name(this.getName()).build())
        .movies(this.getMovies())
        .metadata(this.metadataEntity.toDomain())
        .build();
  }

  private List<MovieFromActor> getMovies() {
    return this.members != null
        ? this.members.stream()
            .map(member -> member.getMovie().toMovieFromActor())
            .sorted(Comparator.comparing(MovieFromActor::getReleaseDate))
            .toList()
        : new ArrayList<>();
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
