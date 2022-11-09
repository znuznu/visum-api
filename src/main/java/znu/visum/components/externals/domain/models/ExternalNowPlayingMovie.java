package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class ExternalNowPlayingMovie {

  private int id;
  private String title;
  private LocalDate releaseDate;
  private String posterUrl;
}
