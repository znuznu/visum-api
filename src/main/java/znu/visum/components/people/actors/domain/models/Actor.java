package znu.visum.components.people.actors.domain.models;

import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.people.domain.models.People;

import java.util.ArrayList;
import java.util.List;

public class Actor extends People {
  List<MovieFromActor> movies;

  public Actor() {
    super();
  }

  public Actor(Long id, String name, String forename, List<MovieFromActor> movies) {
    super(id, name, forename);
    this.movies = movies;
  }

  public static Actor from(ActorFromMovie actorFromMovie) {
    return new Actor(
        actorFromMovie.getId(),
        actorFromMovie.getName(),
        actorFromMovie.getForename(),
        new ArrayList<>());
  }

  public List<MovieFromActor> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieFromActor> movies) {
    this.movies = movies;
  }

  public static final class Builder {
    private final Actor actor;

    public Builder() {
      this.actor = new Actor();
    }

    public Builder movies(List<MovieFromActor> movies) {
      actor.setMovies(movies);
      return this;
    }

    public Builder id(Long id) {
      actor.setId(id);
      return this;
    }

    public Builder name(String name) {
      actor.setName(name);
      return this;
    }

    public Builder forename(String forename) {
      actor.setForename(forename);
      return this;
    }

    public Actor build() {
      return this.actor;
    }
  }
}
