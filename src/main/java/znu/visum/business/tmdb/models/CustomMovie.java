package znu.visum.business.tmdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import znu.visum.business.tmdb.mappers.CastPeople;
import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.CrewPeople;
import znu.visum.business.tmdb.mappers.MovieDetail;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A detailed object composed of multiple TMDB data.
 * <p>
 * /movie/{id} + /movie/{id}/credits
 */
public class CustomMovie {
    @JsonUnwrapped
    @JsonProperty("details")
    private MovieDetail details;

    @JsonProperty("actors")
    private List<CastPeople> actors;

    @JsonProperty("directors")
    private List<CrewPeople> directors;

    public CustomMovie(MovieDetail details, Credits credits) {
        this.details = details;

        if (credits.getCast().size() >= 10) {
            this.actors = credits.getCast().subList(0, 10);
        } else {
            this.actors = credits.getCast().subList(0, credits.getCast().size() - 1);
        }

        this.directors = credits.getCrew()
                .stream()
                .filter(CrewPeople::isDirector)
                .collect(Collectors.toList());
    }

    public MovieDetail getDetails() {
        return details;
    }

    public void setDetails(MovieDetail details) {
        this.details = details;
    }

    public List<CastPeople> getActors() {
        return actors;
    }

    public void setActors(List<CastPeople> actors) {
        this.actors = actors;
    }

    public List<CrewPeople> getDirectors() {
        return directors;
    }

    public void setDirectors(List<CrewPeople> directors) {
        this.directors = directors;
    }
}
