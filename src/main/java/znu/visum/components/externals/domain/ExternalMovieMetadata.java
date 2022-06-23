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

  public MovieMetadata toMovieMetadataWithMovieId(long movieId) {
    String posterUrl = this.getCompletePosterUrl().orElse(null);

    return MovieMetadata.builder()
        .movieId(movieId)
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
}
