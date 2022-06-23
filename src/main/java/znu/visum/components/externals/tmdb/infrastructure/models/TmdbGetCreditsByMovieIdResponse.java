package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.externals.domain.ExternalMovieCredits;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGetCreditsByMovieIdResponse {
  @JsonProperty("cast")
  private List<TmdbCastPeople> cast;

  @JsonProperty("crew")
  private List<TmdbCrewPeople> crew;

  public TmdbGetCreditsByMovieIdResponse(List<TmdbCastPeople> cast, List<TmdbCrewPeople> crew) {
    this.cast = cast;
    this.crew = crew;
  }

  public TmdbGetCreditsByMovieIdResponse() {}

  public List<TmdbCastPeople> getCast() {
    return cast;
  }

  public void setCast(List<TmdbCastPeople> cast) {
    this.cast = cast;
  }

  public List<TmdbCrewPeople> getCrew() {
    return crew;
  }

  public void setCrew(List<TmdbCrewPeople> crew) {
    this.crew = crew;
  }

  public ExternalMovieCredits toDomainWithBasePosterUrl(String basePosterUrl) {
    return ExternalMovieCredits.builder()
        .actors(
            this.cast.stream()
                .distinct()
                .map(TmdbCastPeople::toDomain)
                .collect(Collectors.toList()))
        .directors(
            this.crew.stream()
                .filter(TmdbCrewPeople::isDirector)
                .distinct()
                .map(people -> people.toDomainWithBasePosterUrl(basePosterUrl))
                .collect(Collectors.toList()))
        .build();
  }
}
