package znu.visum.components.external.tmdb.usecases.searchmovies.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.external.domain.models.ExternalMovieFromSearch;

import java.time.LocalDate;

@ApiModel("Represents a page of TMDB movies found on the TMDB research endpoint.")
public class SearchTmdbMoviesResponse {
  @ApiModelProperty("The TMDB identifier of the movie.")
  private final int tmdbId;

  @ApiModelProperty("The title of the movie.")
  private final String title;

  @ApiModelProperty("The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  public SearchTmdbMoviesResponse(int tmdbId, String title, LocalDate releaseDate) {
    this.tmdbId = tmdbId;
    this.title = title;
    this.releaseDate = releaseDate;
  }

  public static SearchTmdbMoviesResponse from(ExternalMovieFromSearch externalMovieFromSearch) {
    return new SearchTmdbMoviesResponse(
        externalMovieFromSearch.getId(),
        externalMovieFromSearch.getTitle(),
        externalMovieFromSearch.getReleaseDate());
  }

  public int getTmdbId() {
    return tmdbId;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }
}
