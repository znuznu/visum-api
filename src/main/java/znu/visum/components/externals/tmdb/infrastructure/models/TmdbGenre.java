package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import znu.visum.components.genres.domain.Genre;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class TmdbGenre {
  private int id;

  @JsonProperty("name")
  private String name;

  public Genre toDomain() {
    return new Genre(null, name);
  }

  @JsonIgnore
  public int getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
