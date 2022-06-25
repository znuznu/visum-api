package znu.visum.components.externals.domain;

import lombok.Getter;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.people.directors.domain.DirectorMetadata;

@Getter
public class ExternalDirector extends ExternalPeople {
  private final String posterUrl;

  public ExternalDirector(long id, String forename, String name, String posterUrl) {
    super(id, forename, name);
    this.posterUrl = posterUrl;
  }

  public DirectorFromMovie toDirectorFromMovie() {
    return DirectorFromMovie.builder()
        .id(null)
        .forename(this.getForename())
        .name(this.getName())
        .metadata(
            DirectorMetadata.builder().posterUrl(this.getPosterUrl()).tmdbId(this.getId()).build())
        .build();
  }
}
