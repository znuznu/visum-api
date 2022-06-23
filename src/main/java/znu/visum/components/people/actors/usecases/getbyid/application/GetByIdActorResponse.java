package znu.visum.components.people.actors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.people.actors.domain.Actor;
import znu.visum.components.people.actors.domain.MovieFromActor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Schema(description = "Represents an actor.")
public class GetByIdActorResponse {
  @Schema(description = "The actor identifier.")
  private final long id;

  @Schema(description = "The actor name.")
  private final String name;

  @Schema(description = "The actor forename.")
  private final String forename;

  @Schema(description = "The actor movies.")
  private final List<ResponseMovie> movies;

  public static GetByIdActorResponse from(Actor actor) {
    return new GetByIdActorResponse(
        actor.getId(),
        actor.getName(),
        actor.getForename(),
        actor.getMovies().stream().map(ResponseMovie::from).collect(Collectors.toList()));
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovie {

    private final Long id;
    private final String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate releaseDate;

    private final boolean isFavorite;
    private final boolean shouldWatch;

    static ResponseMovie from(MovieFromActor movieFromActor) {
      return new ResponseMovie(
          movieFromActor.getId(),
          movieFromActor.getTitle(),
          movieFromActor.getReleaseDate(),
          movieFromActor.isFavorite(),
          movieFromActor.isToWatch());
    }
  }
}
