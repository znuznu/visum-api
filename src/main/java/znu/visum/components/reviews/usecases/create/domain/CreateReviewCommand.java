package znu.visum.components.reviews.usecases.create.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateReviewCommand {

  private final int grade;
  private final String content;
  private final long movieId;
}
