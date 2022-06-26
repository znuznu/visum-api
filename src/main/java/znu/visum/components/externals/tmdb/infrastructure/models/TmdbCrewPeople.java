package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import znu.visum.components.externals.domain.ExternalDirector;
import znu.visum.components.person.domain.Identity;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class TmdbCrewPeople extends TmdbCreditsPeople {
  @JsonProperty("job")
  private String job;

  @JsonProperty("profile_path")
  private String profilePath;

  public TmdbCrewPeople() {
    super();
  }

  public boolean isDirector() {
    return this.job.equalsIgnoreCase("Director");
  }

  public ExternalDirector toDomainWithBasePosterUrl(String basePosterUrl) {
    if (!this.isDirector()) {
      throw new IllegalArgumentException("Could not map a crew people that isn't a director.");
    }

    String posterUrl = profilePath != null ? basePosterUrl + profilePath : null;

    return new ExternalDirector(this.getId(), Identity.fromPlain(this.getName()), posterUrl);
  }
}
