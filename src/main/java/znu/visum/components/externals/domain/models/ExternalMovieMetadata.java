package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Builder
@Getter
public class ExternalMovieMetadata {

  private Long tmdbId;
  private String imdbId;
  private String originalLanguage;
  private String tagline;
  private String overview;
  private long budget;
  private long revenue;
  private String basePosterUrl;
  private String posterPath;
  private int runtime;

  public Optional<String> getCompletePosterUrl() {
    if (this.basePosterUrl == null || this.posterPath == null) {
      return Optional.empty();
    }

    return Optional.of(this.basePosterUrl + "" + this.posterPath);
  }

  public void setPosterBaseUrl(String basePosterUrl) {
    this.basePosterUrl = basePosterUrl;
  }
}
