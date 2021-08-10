package znu.visum.business.tmdb.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credits {
    @JsonProperty("cast")
    private List<CastPeople> cast;

    @JsonProperty("crew")
    private List<CrewPeople> crew;

    public Credits(List<CastPeople> cast, List<CrewPeople> crew) {
        this.cast = cast;
        this.crew = crew;
    }

    public Credits() {
    }

    public List<CastPeople> getCast() {
        return cast;
    }

    public void setCast(List<CastPeople> cast) {
        this.cast = cast;
    }

    public List<CrewPeople> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewPeople> crew) {
        this.crew = crew;
    }
}
