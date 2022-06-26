package znu.visum.components.externals.domain;

import lombok.Getter;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.person.actors.domain.ActorMetadata;
import znu.visum.components.person.domain.Identity;

@Getter
public class ExternalActor extends ExternalPeople {
  private final String posterUrl;

  public ExternalActor(long id, Identity identity, String posterUrl) {
    super(id, identity);
    this.posterUrl = posterUrl;
  }

  public ActorFromMovie toActorFromMovie() {
    return ActorFromMovie.builder()
        .id(null)
        .identity(this.identity)
        .metadata(ActorMetadata.builder().tmdbId(this.id).posterUrl(this.posterUrl).build())
        .build();
  }
}
