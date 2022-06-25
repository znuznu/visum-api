package znu.visum.components.externals.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.MovieMetadata;

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
  private String posterBaseUrl;
  private String posterPath;
  private int runtime;

  public Optional<String> getCompletePosterUrl() {
    if (this.posterBaseUrl == null || this.posterPath == null) {
      return Optional.empty();
    }

    return Optional.of(this.posterBaseUrl + "" + this.posterPath);
  }

  public void setPosterBaseUrl(String posterBaseUrl) {
    this.posterBaseUrl = posterBaseUrl;
  }

  public MovieMetadata toMovieMetadata() {
    String posterUrl = this.getCompletePosterUrl().orElse(null);

    return MovieMetadata.builder()
        .movieId(null)
        .tmdbId(this.getTmdbId())
        .imdbId(this.getImdbId())
        .runtime(this.getRuntime())
        .revenue(this.getRevenue())
        .originalLanguage(this.getOriginalLanguage())
        .tagline(this.getTagline())
        .budget(this.getBudget())
        .overview(this.getOverview())
        .posterUrl(posterUrl)
        .build();
  }

  public MovieMetadata toMovieMetadataWithMovieId(long movieId) {
    var metadata = this.toMovieMetadata();
    metadata.setMovieId(movieId);

    return metadata;
  }
}
