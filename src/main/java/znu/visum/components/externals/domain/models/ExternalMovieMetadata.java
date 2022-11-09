package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.MovieMetadata;

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
  private String posterUrl;
  private int runtime;

  public MovieMetadata toMovieMetadata() {
    return MovieMetadata.builder()
        .movieId(null)
        .tmdbId(this.tmdbId)
        .imdbId(this.imdbId)
        .runtime(this.runtime)
        .revenue(this.revenue)
        .originalLanguage(this.originalLanguage)
        .tagline(this.tagline)
        .budget(this.budget)
        .overview(this.overview)
        .posterUrl(this.posterUrl)
        .build();
  }

  public MovieMetadata toMovieMetadataWithMovieId(long movieId) {
    var metadata = this.toMovieMetadata();
    metadata.setMovieId(movieId);

    return metadata;
  }
}
