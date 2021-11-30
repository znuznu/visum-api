package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGetConfigurationResponse {
  @JsonProperty("images")
  private TmdbGetConfigurationImages images;

  public TmdbGetConfigurationResponse() {}

  public TmdbGetConfigurationResponse(TmdbGetConfigurationImages images) {
    this.images = images;
  }

  public TmdbGetConfigurationImages getImages() {
    return images;
  }

  public void setImages(TmdbGetConfigurationImages images) {
    this.images = images;
  }
}
