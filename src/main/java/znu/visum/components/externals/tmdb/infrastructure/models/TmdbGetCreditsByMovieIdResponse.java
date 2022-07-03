package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import znu.visum.components.externals.domain.ExternalCast;
import znu.visum.components.externals.domain.ExternalMovieCredits;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TmdbGetCreditsByMovieIdResponse {
  @JsonProperty("cast")
  private List<TmdbCastPeople> cast;

  @JsonProperty("crew")
  private List<TmdbCrewPeople> crew;

  public ExternalMovieCredits toDomainWithRootUrl(String basePosterUrl) {
    return ExternalMovieCredits.builder()
        .cast(
            ExternalCast.of(
                this.cast.stream()
                    .distinct()
                    .map(people -> people.toDomainWithRootUrl(basePosterUrl))
                    .collect(Collectors.toList())))
        .directors(
            this.crew.stream()
                .filter(TmdbCrewPeople::isDirector)
                .distinct()
                .map(people -> people.toDomainWithBasePosterUrl(basePosterUrl))
                .collect(Collectors.toList()))
        .build();
  }
}
