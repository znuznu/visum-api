package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.externals.domain.models.ExternalActor;

// Not useful yet, we might want to save the character later
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCastPeople extends TmdbCreditsPeople {
  @JsonProperty("character")
  private String character;

  public TmdbCastPeople() {
    super();
  }

  public String getCharacter() {
    return character;
  }

  public void setCharacter(String character) {
    this.character = character;
  }

  public ExternalActor toDomain() {
    String trimName = this.getName().trim();
    int firstDelimiterIndex = trimName.indexOf(" ");

    if (firstDelimiterIndex < 0) {
      return new ExternalActor(this.getId(), trimName, "");
    }

    return new ExternalActor(
        this.getId(),
        trimName.substring(0, firstDelimiterIndex),
        trimName.substring(firstDelimiterIndex + 1));
  }
}
