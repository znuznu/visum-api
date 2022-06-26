package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.externals.domain.ExternalActor;
import znu.visum.components.person.domain.Identity;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TmdbCastPeople extends TmdbCreditsPeople {
  @JsonProperty("character")
  private String character;

  @JsonProperty("profile_path")
  private String profilePath;

  public TmdbCastPeople() {
    super();
  }

  public ExternalActor toDomainWithRootUrl(String basePosterUrl) {
    String posterUrl = profilePath != null ? basePosterUrl + profilePath : null;

    return new ExternalActor(this.getId(), Identity.fromPlain(this.getName()), posterUrl);
  }
}
