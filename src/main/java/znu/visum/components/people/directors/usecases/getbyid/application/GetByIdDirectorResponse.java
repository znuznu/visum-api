package znu.visum.components.people.directors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.MovieFromDirector;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a director.")
public class GetByIdDirectorResponse {
  @Schema(description = "The director's identifier.")
  private final long id;

  @Schema(description = "The director's name.")
  private final String name;

  @Schema(description = "The director's forename.")
  private final String forename;

  @Schema(description = "The director's movies.")
  private List<ResponseMovie> movies;

  @Schema(description = "The director's TMDb id.")
  private long tmdbId;

  @Schema(description = "The director's poster URL.")
  private String posterUrl;

  public static GetByIdDirectorResponse from(Director director) {
    return GetByIdDirectorResponse.builder()
        .id(director.getId())
        .name(director.getName())
        .forename(director.getForename())
        .movies(director.getMovies().stream().map(ResponseMovie::from).collect(Collectors.toList()))
        .tmdbId(director.getMetadata().getTmdbId())
        .posterUrl(director.getMetadata().getPosterUrl())
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

    static ResponseMovie from(MovieFromDirector movieFromDirector) {
      return new ResponseMovie(
          movieFromDirector.getId(),
          movieFromDirector.getTitle(),
          movieFromDirector.getReleaseDate(),
          movieFromDirector.isFavorite(),
          movieFromDirector.isToWatch());
    }
  }
}
