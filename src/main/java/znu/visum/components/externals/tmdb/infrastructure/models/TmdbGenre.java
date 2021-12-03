package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.genres.domain.models.Genre;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGenre {
  private int id;

  @JsonProperty("name")
  private String name;

  public TmdbGenre() {}

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
