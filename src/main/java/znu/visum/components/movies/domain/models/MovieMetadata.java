package znu.visum.components.movies.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MovieMetadata {

  private Long id;
  private Long movieId;
  private Long tmdbId;
  private String imdbId;
  private String originalLanguage;
  private String tagline;
  private String overview;
  private long budget;
  private long revenue;
  private String posterUrl;
  private int runtime;
}
