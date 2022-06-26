package znu.visum.components.externals.domain;

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
  private String posterUrl;
}
