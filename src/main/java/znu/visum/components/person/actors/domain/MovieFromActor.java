package znu.visum.components.person.actors.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class MovieFromActor {

  private Long id;
  private String title;
  private LocalDate releaseDate;
  private boolean isFavorite;
  private boolean isToWatch;
}
