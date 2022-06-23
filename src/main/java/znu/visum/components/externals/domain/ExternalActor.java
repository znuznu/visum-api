package znu.visum.components.externals.domain;

import lombok.Getter;

@Getter
public class ExternalActor extends ExternalPeople {
  private final String posterUrl;

  public ExternalActor(long id, String forename, String name, String posterUrl) {
    super(id, forename, name);
    this.posterUrl = posterUrl;
  }
}
