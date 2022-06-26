package znu.visum.components.reviews.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class Review {

  private Long id;
  private String content;
  private LocalDateTime updateDate;
  private LocalDateTime creationDate;
  private Grade grade;
  private MovieFromReview movie;
}
