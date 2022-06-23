package znu.visum.components.people.actors.usecases.deprecated.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.actors.domain.MovieFromActor;

import java.util.List;

public class CreateActorRequest {
  private final String name;

  private final String forename;

  private final List<MovieFromActor> movies;

  @JsonCreator
  public CreateActorRequest(
      @JsonProperty("name") String name,
      @JsonProperty("forename") String forename,
      @JsonProperty("movies") List<MovieFromActor> movies) {
    this.name = name;
    this.forename = forename;
    this.movies = movies;
  }

  public Actor toDomain() {
    return Actor.builder().name(this.name).forename(this.forename).movies(this.movies).build();
  }
}
