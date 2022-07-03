package znu.visum.components.movies.infrastructure;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import znu.visum.components.movies.domain.CastMember;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.actors.infrastructure.ActorEntity;
import znu.visum.components.person.domain.Identity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cast_member")
public class CastMemberEntity {

  @EmbeddedId private CastMemberId castMemberId = new CastMemberId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("movieId")
  private MovieEntity movie;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("actorId")
  private ActorEntity actor;

  @Column(name = "role_order")
  private int roleOrder;

  @CreationTimestamp private LocalDateTime creationDate;

  @UpdateTimestamp private LocalDateTime updateDate;

  public CastMember toDomain() {
    return CastMember.builder()
        .movieId(this.getCastMemberId().getMovieId())
        .actorId(this.getCastMemberId().getActorId())
        .identity(
            Identity.builder()
                .forename(this.actor.getForename())
                .name(this.actor.getName())
                .build())
        .posterUrl(this.actor.getMetadataEntity().getPosterUrl())
        .role(new Role(this.getCastMemberId().getCharacter(), this.getRoleOrder()))
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

    CastMemberEntity other = (CastMemberEntity) obj;
    if (this.getCastMemberId() == null) {
      return false;
    } else {
      return this.castMemberId.equals(other.getCastMemberId());
    }
  }
}
