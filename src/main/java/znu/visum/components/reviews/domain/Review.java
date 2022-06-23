package znu.visum.components.reviews.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Review {

  private Long id;
  @Setter private String content;
  @Setter private LocalDateTime updateDate;
  private LocalDateTime creationDate;
  @Setter private int grade;
  private MovieFromReview movie;
}
