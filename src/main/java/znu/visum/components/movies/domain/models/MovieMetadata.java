package znu.visum.components.movies.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
public class MovieMetadata {

  @Setter private Long movieId;
  @Setter private Long tmdbId;
  private String imdbId;
  private String originalLanguage;
  private String tagline;
  private String overview;
  private long budget;
  private long revenue;
  private String posterUrl;
  private int runtime;
}
