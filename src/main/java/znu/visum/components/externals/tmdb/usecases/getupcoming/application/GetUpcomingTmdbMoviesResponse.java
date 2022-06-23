package znu.visum.components.externals.tmdb.usecases.getupcoming.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.externals.domain.ExternalUpcomingMovie;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a page of TMDb movies found on the TMDb upcoming endpoint.")
public class GetUpcomingTmdbMoviesResponse {
  @Schema(description = "The TMDB identifier of the movie.")
  private final int tmdbId;

  @Schema(description = "The title of the movie.")
  private final String title;

  @Schema(description = "The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @Schema(description = "The TMDb poster's URL of the movie.")
  private final String posterUrl;

  public static GetUpcomingTmdbMoviesResponse from(ExternalUpcomingMovie movie) {
    return new GetUpcomingTmdbMoviesResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.hasCompletePosterUrl()
            ? movie.getBasePosterUrl() + "" + movie.getPosterPath()
            : null);
  }
}
