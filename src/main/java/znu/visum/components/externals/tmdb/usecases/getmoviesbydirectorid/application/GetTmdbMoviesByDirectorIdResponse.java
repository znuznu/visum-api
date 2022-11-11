package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a list of TMDb movies made by a director.")
public class GetTmdbMoviesByDirectorIdResponse {

  private final List<MovieByDirector> movies;

  public static GetTmdbMoviesByDirectorIdResponse from(List<ExternalDirectorMovie> movies) {
    return new GetTmdbMoviesByDirectorIdResponse(
        movies.stream().map(MovieByDirector::from).toList());
  }

  @AllArgsConstructor
  @Builder
  @Getter
  static class MovieByDirector {
    @Schema(description = "The TMDb identifier of the movie.")
    private final long tmdbId;

    @Schema(description = "The title of the movie.")
    private final String title;

    @Schema(description = "The release date of the movie.")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate releaseDate;

    @Schema(description = "The TMDb poster's URL of the movie.")
    private final String posterUrl;

    public static MovieByDirector from(ExternalDirectorMovie movie) {
      return MovieByDirector.builder()
          .tmdbId(movie.getId())
          .title(movie.getTitle())
          .posterUrl(movie.getPosterUrl())
          .releaseDate(movie.getReleaseDate())
          .build();
    }
  }
}
