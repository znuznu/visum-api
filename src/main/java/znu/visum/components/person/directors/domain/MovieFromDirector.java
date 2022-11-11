package znu.visum.components.person.directors.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class MovieFromDirector {

  private Long id;
  private Long tmdbId;
  private String title;
  private LocalDate releaseDate;
  private boolean isFavorite;
  private boolean isToWatch;
}
