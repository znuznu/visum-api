package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ExternalMovie {

  @Setter private ExternalMovieCredits credits;
  private String id;
  private String title;
  private LocalDate releaseDate;
  private List<String> genres;
  private ExternalMovieMetadata metadata;
}
