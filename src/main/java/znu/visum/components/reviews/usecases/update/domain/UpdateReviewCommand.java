package znu.visum.components.reviews.usecases.update.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateReviewCommand {

  private final long id;
  private final int grade;
  private final String content;
}
