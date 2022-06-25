package znu.visum.components.externals.domain;

import lombok.Getter;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.people.actors.domain.ActorMetadata;

@Getter
public class ExternalActor extends ExternalPeople {
  private final String posterUrl;

  public ExternalActor(long id, String forename, String name, String posterUrl) {
    super(id, forename, name);
    this.posterUrl = posterUrl;
  }

  public ActorFromMovie toActorFromMovie() {
    return ActorFromMovie.builder()
        .id(null)
        .forename(this.getForename())
        .name(this.getName())
        .metadata(
            ActorMetadata.builder().tmdbId(this.getId()).posterUrl(this.getPosterUrl()).build())
        .build();
  }
}
