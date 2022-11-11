package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class ExternalDirectorMovie {

  private long id;
  private String title;
  private LocalDate releaseDate;
  private String posterUrl;
}
