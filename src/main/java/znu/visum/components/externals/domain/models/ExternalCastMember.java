package znu.visum.components.externals.domain.models;

import lombok.Getter;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorMetadata;
import znu.visum.components.person.domain.Identity;

import java.util.ArrayList;

@Getter
public class ExternalCastMember extends ExternalPeople {

  private final Role role;
  private final String posterUrl;

  public ExternalCastMember(long id, Identity identity, Role role, String posterUrl) {
    super(id, identity);
    this.role = role;
    this.posterUrl = posterUrl;
  }

  public Actor toActor() {
    return Actor.builder()
        .id(null)
        .identity(this.getIdentity())
        .movies(new ArrayList<>())
        .metadata(
            ActorMetadata.builder().tmdbId(this.getId()).posterUrl(this.getPosterUrl()).build())
        .build();
  }
}
