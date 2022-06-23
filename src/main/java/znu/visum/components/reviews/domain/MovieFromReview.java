package znu.visum.components.reviews.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MovieFromReview {

  private long id;
  private String title;
  private LocalDate releaseDate;
  private MovieFromReviewMetadata metadata;

  @AllArgsConstructor
  @Getter
  public static class MovieFromReviewMetadata {

    private final String posterUrl;
  }
}
