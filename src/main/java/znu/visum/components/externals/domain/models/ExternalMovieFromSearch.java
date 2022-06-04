package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a movie that don't (necessarily) belong to the Visum database. Comes from the search
 * engine of an API like the TMDb one.
 */
@AllArgsConstructor
@Builder
@Getter
public class ExternalMovieFromSearch {

  private int id;
  private String title;
  private LocalDate releaseDate;
  // Right part of a URL (eg: /something1234 for TMDb)
  private String posterPath;
  // Left part of a URL (eg: https://image.tmdb.org/t/p/w780 for TMDb)
  private String basePosterUrl;

  public boolean hasCompletePosterUrl() {
    return this.getBasePosterUrl() != null && this.getPosterPath() != null;
  }
}
