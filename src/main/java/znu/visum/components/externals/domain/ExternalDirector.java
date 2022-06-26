package znu.visum.components.externals.domain;

import lombok.Getter;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.person.directors.domain.DirectorMetadata;
import znu.visum.components.person.domain.Identity;

@Getter
public class ExternalDirector extends ExternalPeople {
  private final String posterUrl;

  public ExternalDirector(long id, Identity identity, String posterUrl) {
    super(id, identity);
    this.posterUrl = posterUrl;
  }

  public DirectorFromMovie toDirectorFromMovie() {
    return DirectorFromMovie.builder()
        .id(null)
        .identity(this.identity)
        .metadata(DirectorMetadata.builder().posterUrl(this.posterUrl).tmdbId(this.id).build())
        .build();
  }
}
