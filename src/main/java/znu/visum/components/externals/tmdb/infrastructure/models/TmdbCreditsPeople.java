package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TmdbCreditsPeople {
  @JsonProperty("id")
  private int id;

  @JsonProperty("gender")
  private int gender;

  @JsonProperty("name")
  private String name;

  @JsonProperty("order")
  private int order;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof TmdbCreditsPeople)) {
      return false;
    }

    TmdbCreditsPeople that = (TmdbCreditsPeople) o;

    return getId() == that.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
