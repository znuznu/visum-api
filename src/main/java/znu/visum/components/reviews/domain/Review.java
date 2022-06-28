package znu.visum.components.reviews.domain;

import lombok.Builder;
import lombok.Getter;
import znu.visum.core.assertions.VisumAssert;

import java.time.LocalDateTime;

@Builder
@Getter
public class Review {

  private Long id;
  private Content content;
  private LocalDateTime updateDate;
  private LocalDateTime creationDate;
  private Grade grade;
  private MovieFromReview movie;

  public Review(
      Long id,
      Content content,
      LocalDateTime updateDate,
      LocalDateTime creationDate,
      Grade grade,
      MovieFromReview movie) {
    VisumAssert.notNull("movie", movie);
    VisumAssert.notNull("content", content);
    VisumAssert.notNull("grade", grade);

    this.id = id;
    this.content = content;
    this.updateDate = updateDate;
    this.creationDate = creationDate;
    this.grade = grade;
    this.movie = movie;
  }
}
