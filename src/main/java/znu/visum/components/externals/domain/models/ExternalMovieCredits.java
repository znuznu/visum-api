package znu.visum.components.externals.domain.models;

import java.util.List;

public class ExternalMovieCredits {
  List<ExternalActor> actors;

  List<ExternalDirector> directors;

  public ExternalMovieCredits() {}

  public List<ExternalActor> getActors() {
    return actors;
  }

  public void setActors(List<ExternalActor> actors) {
    this.actors = actors;
  }

  public List<ExternalDirector> getDirectors() {
    return directors;
  }

  public void setDirectors(List<ExternalDirector> directors) {
    this.directors = directors;
  }

  public static final class Builder {
    ExternalMovieCredits credits;

    public Builder() {
      this.credits = new ExternalMovieCredits();
    }

    public Builder actors(List<ExternalActor> actors) {
      this.credits.actors = actors;
      return this;
    }

    public Builder directors(List<ExternalDirector> directors) {
      this.credits.directors = directors;
      return this;
    }

    public ExternalMovieCredits build() {
      return this.credits;
    }
  }
}
