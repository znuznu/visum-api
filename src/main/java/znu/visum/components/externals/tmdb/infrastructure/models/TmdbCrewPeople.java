package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import znu.visum.components.externals.domain.models.ExternalDirector;

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
      throw new IllegalArgumentException();
    }

    String posterUrl = profilePath != null ? basePosterUrl + profilePath : null;

    String trimName = this.getName().trim();
    int firstDelimiterIndex = trimName.indexOf(" ");

    if (firstDelimiterIndex < 0) {
      return new ExternalDirector(this.getId(), trimName, "", posterUrl);
    }

    return new ExternalDirector(
        this.getId(),
        trimName.substring(0, firstDelimiterIndex),
        trimName.substring(firstDelimiterIndex + 1),
        posterUrl);
  }
}
