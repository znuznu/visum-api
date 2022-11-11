package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGetPersonMovieCreditsResponse {

  @JsonProperty("crew")
  private List<TmdbPersonMovie> crew;

  public List<ExternalDirectorMovie> toDomainWithRootUrl(String basePosterUrl) {
    return this.crew.stream()
        .filter(TmdbPersonMovie::isDirector)
        .map(movie -> movie.toDomainWithRootUrl(basePosterUrl))
        .toList();
  }
}
