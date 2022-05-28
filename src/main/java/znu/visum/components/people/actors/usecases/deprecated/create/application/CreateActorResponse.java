package znu.visum.components.people.actors.usecases.deprecated.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.models.MovieFromActor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreateActorResponse {
  private final long id;

  private final String forename;

  private final String name;

  private final List<ResponseMovie> movies;

  public CreateActorResponse(long id, String name, String forename, List<ResponseMovie> movies) {
    this.id = id;
    this.forename = forename;
    this.name = name;
    this.movies = movies;
  }

  public static CreateActorResponse from(Actor actor) {
    return new CreateActorResponse(
        actor.getId(),
        actor.getName(),
        actor.getForename(),
        actor.getMovies().stream().map(ResponseMovie::from).collect(Collectors.toList()));
  }

  public long getId() {
    return id;
  }

  public String getForename() {
    return forename;
  }

  public String getName() {
    return name;
  }

  public List<ResponseMovie> getMovies() {
    return movies;
  }

  private static class ResponseMovie {
    private final Long id;

    private final String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate releaseDate;

    private final boolean isFavorite;

    private final boolean shouldWatch;

    public ResponseMovie(
        Long id, String title, LocalDate releaseDate, boolean isFavorite, boolean shouldWatch) {
      this.id = id;
      this.title = title;
      this.releaseDate = releaseDate;
      this.isFavorite = isFavorite;
      this.shouldWatch = shouldWatch;
    }

    static ResponseMovie from(MovieFromActor movieFromActor) {
      return new ResponseMovie(
          movieFromActor.getId(),
          movieFromActor.getTitle(),
          movieFromActor.getReleaseDate(),
          movieFromActor.isFavorite(),
          movieFromActor.isToWatch());
    }

    public Long getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public LocalDate getReleaseDate() {
      return releaseDate;
    }

    public boolean isFavorite() {
      return isFavorite;
    }

    public boolean isShouldWatch() {
      return shouldWatch;
    }
  }
}
