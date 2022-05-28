package znu.visum.components.externals.tmdb.usecases.searchmovies.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a page of TMDB movies found on the TMDB research endpoint.")
public class SearchTmdbMoviesResponse {
  @Schema(description = "The TMDB identifier of the movie.")
  private final int tmdbId;

  @Schema(description = "The title of the movie.")
  private final String title;

  @Schema(description = "The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @Schema(description = "The TMDb poster's URL of the movie.")
  private final String posterUrl;

  public static SearchTmdbMoviesResponse from(ExternalMovieFromSearch externalMovieFromSearch) {
    return new SearchTmdbMoviesResponse(
        externalMovieFromSearch.getId(),
        externalMovieFromSearch.getTitle(),
        externalMovieFromSearch.getReleaseDate(),
        externalMovieFromSearch.hasCompletePosterUrl()
            ? externalMovieFromSearch.getBasePosterUrl()
                + ""
                + externalMovieFromSearch.getPosterPath()
            : null);
  }
}
