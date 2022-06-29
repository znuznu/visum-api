package znu.visum.components.person.actors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.MovieFromActor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents an actor.")
public class GetByIdActorResponse {

  @Schema(description = "The actor's identifier.")
  private final long id;

  @Schema(description = "The actor's name.")
  private final String name;

  @Schema(description = "The actor's forename.")
  private final String forename;

  @Schema(description = "The actor's movies.")
  private final List<ResponseMovie> movies;

  @Schema(description = "The actor's TMDb id.")
  private long tmdbId;

  @Schema(description = "The actor's poster URL.")
  private String posterUrl;

  public static GetByIdActorResponse from(Actor actor) {
    return GetByIdActorResponse.builder()
        .id(actor.getId())
        .name(actor.getIdentity().getName())
        .forename(actor.getIdentity().getForename())
        .movies(
            actor.getMovies().stream()
                .map(GetByIdActorResponse.ResponseMovie::from)
                .collect(Collectors.toList()))
        .tmdbId(actor.getMetadata().getTmdbId())
        .posterUrl(actor.getMetadata().getPosterUrl())
        .build();
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
