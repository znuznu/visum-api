package znu.visum.components.externals.domain.models;

import lombok.Getter;

@Getter
public class ExternalDirector extends ExternalPeople {
  private final String posterUrl;

  public ExternalDirector(long id, String forename, String name, String posterUrl) {
    super(id, forename, name);
    this.posterUrl = posterUrl;
  }
}
