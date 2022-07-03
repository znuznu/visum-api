package znu.visum.components.externals.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ExternalMovieCredits {

  private final ExternalCast cast;
  private final List<ExternalDirector> directors;
}
