package znu.visum.components.externals.tmdb.usecases.getnowplaying.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.externals.domain.models.ExternalNowPlayingMovie;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a page of TMDb movies found on the TMDb now playing endpoint.")
public class GetNowPlayingTmdbMoviesResponse {
  @Schema(description = "The TMDb identifier of the movie.")
  private final int tmdbId;

  @Schema(description = "The title of the movie.")
  private final String title;

  @Schema(description = "The release date of the movie.")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private final LocalDate releaseDate;

  @Schema(description = "The TMDb poster's URL of the movie.")
  private final String posterUrl;

  public static GetNowPlayingTmdbMoviesResponse from(ExternalNowPlayingMovie movie) {
    return new GetNowPlayingTmdbMoviesResponse(
        movie.getId(), movie.getTitle(), movie.getReleaseDate(), movie.getPosterUrl());
  }
}
