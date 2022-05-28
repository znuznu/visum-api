package znu.visum.components.people.directors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a director.")
public class GetByIdDirectorResponse {
  @Schema(description = "The director identifier.")
  private final long id;

  @Schema(description = "The director name.")
  private final String name;

  @Schema(description = "The director forename.")
  private final String forename;

  @Schema(description = "The director movies.")
  private List<ResponseMovie> movies;

  public static GetByIdDirectorResponse from(Director director) {
    return new GetByIdDirectorResponse(
        director.getId(),
        director.getName(),
        director.getForename(),
        director.getMovies().stream().map(ResponseMovie::from).collect(Collectors.toList()));
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
