package znu.visum.components.people.directors.usecases.deprecated.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;

import java.util.List;

public class CreateDirectorRequest {
  private final String name;

  private final String forename;

  private final List<MovieFromDirector> movies;

  @JsonCreator
  public CreateDirectorRequest(
      @JsonProperty("name") String name,
      @JsonProperty("forename") String forename,
      @JsonProperty("movies") List<MovieFromDirector> movies) {
    this.name = name;
    this.forename = forename;
    this.movies = movies;
  }

  public Director toDomain() {
    return new Director(null, this.name, this.forename, this.movies);
  }
}
