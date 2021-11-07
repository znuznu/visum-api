package znu.visum.components.external.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.external.domain.models.ExternalDirector;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCrewPeople extends TmdbCreditsPeople {
  @JsonProperty("job")
  private String job;

  public TmdbCrewPeople() {
    super();
  }

  public void setJob(String job) {
    this.job = job;
  }

  public boolean isDirector() {
    return this.job.equalsIgnoreCase("Director");
  }

  public ExternalDirector toDomainExternalDirector() {
    if (!this.isDirector()) {
      throw new IllegalArgumentException();
    }

    String trimName = this.getName().trim();
    int firstDelimiterIndex = trimName.indexOf(" ");

    if (firstDelimiterIndex < 0) {
      return new ExternalDirector(this.getId(), trimName, "");
    }

    return new ExternalDirector(
        this.getId(),
        trimName.substring(0, firstDelimiterIndex),
        trimName.substring(firstDelimiterIndex + 1));
  }
}
