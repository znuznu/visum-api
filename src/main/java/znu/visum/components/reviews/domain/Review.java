package znu.visum.components.reviews.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Review {

  private Long id;
  private String content;
  private LocalDateTime updateDate;
  private LocalDateTime creationDate;
  private Grade grade;
  private MovieFromReview movie;

  public Review(
      Long id,
      String content,
      LocalDateTime updateDate,
      LocalDateTime creationDate,
      Grade grade,
      MovieFromReview movie) {
    this.id = id;
    this.content = content;
    this.updateDate = updateDate;
    this.creationDate = creationDate;
    this.grade = grade;
    this.movie = movie;
  }
}
