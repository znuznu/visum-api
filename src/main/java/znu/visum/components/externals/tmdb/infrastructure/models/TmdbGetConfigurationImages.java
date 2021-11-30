package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGetConfigurationImages {

  @JsonProperty("base_url")
  private String baseUrl;

  private String secureBaseUrl;

  private List<String> posterSizes;

  public TmdbGetConfigurationImages() {}

  public TmdbGetConfigurationImages(
      String baseUrl, String secureBaseUrl, List<String> posterSizes) {
    this.baseUrl = baseUrl;
    this.secureBaseUrl = secureBaseUrl;
    this.posterSizes = posterSizes;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @JsonProperty("secureBaseUrl")
  public String getSecureBaseUrl() {
    return secureBaseUrl;
  }

  @JsonProperty("secure_base_url")
  public void setSecureBaseUrl(String secureBaseUrl) {
    this.secureBaseUrl = secureBaseUrl;
  }

  @JsonProperty("posterSizes")
  public List<String> getPosterSizes() {
    return posterSizes;
  }

  @JsonProperty("poster_sizes")
  public void setPosterSizes(List<String> posterSizes) {
    this.posterSizes = posterSizes;
  }
}
