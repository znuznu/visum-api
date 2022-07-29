package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class PageMovie {

  private final long id;
  private final String title;
  private final LocalDate releaseDate;
  private final LocalDateTime creationDate;
  private final String posterUrl;
  private final boolean isFavorite;
  private final boolean isToWatch;
}
