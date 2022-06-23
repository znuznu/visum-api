package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.externals.domain.ExternalActor;

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

  public ExternalActor toDomainWithBasePosterUrl(String basePosterUrl) {
    String posterUrl = profilePath != null ? basePosterUrl + profilePath : null;

    String trimName = this.getName().trim();
    int firstDelimiterIndex = trimName.indexOf(" ");

    if (firstDelimiterIndex < 0) {
      return new ExternalActor(this.getId(), trimName, "", posterUrl);
    }

    return new ExternalActor(
        this.getId(),
        trimName.substring(0, firstDelimiterIndex),
        trimName.substring(firstDelimiterIndex + 1),
        posterUrl);
  }
}
