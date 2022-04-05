package znu.visum.components.people.actors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.models.MovieFromActor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Represents an actor.")
public class GetByIdActorResponse {
  @Schema(description = "The actor identifier.")
  private final long id;

  @Schema(description = "The actor forename.")
  private final String forename;

  @Schema(description = "The actor name.")
  private final String name;

  @Schema(description = "The actor movies.")
  private final List<ResponseMovie> movies;

  public GetByIdActorResponse(long id, String name, String forename, List<ResponseMovie> movies) {
    this.id = id;
    this.name = name;
    this.forename = forename;
    this.movies = movies;
  }

  public static GetByIdActorResponse from(Actor actor) {
    return new GetByIdActorResponse(
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

  public static class ResponseMovie {
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
          movieFromActor.isShouldWatch());
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
