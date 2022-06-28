package znu.visum.components.reviews.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

import java.time.LocalDateTime;

class ReviewUnitTest {

  @Test
  void shouldNotCreateWithoutMovie() {
    Assertions.assertThatThrownBy(
            () ->
                Review.builder()
                    .creationDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .updateDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .grade(Grade.of(3))
                    .content(Content.of("Some"))
                    .movie(null)
                    .build())
        .isInstanceOf(MissingMandatoryFieldException.class);
  }

  @Test
  void shouldNotCreateWithoutGrade() {
    Assertions.assertThatThrownBy(
            () ->
                Review.builder()
                    .creationDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .updateDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .grade(null)
                    .content(Content.of("Some"))
                    .movie(MovieFromReview.builder().build())
                    .build())
        .isInstanceOf(MissingMandatoryFieldException.class);
  }

  @Test
  void shouldNotCreateWithoutContent() {
    Assertions.assertThatThrownBy(
            () ->
                Review.builder()
                    .creationDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .updateDate(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                    .grade(Grade.of(3))
                    .content(null)
                    .movie(MovieFromReview.builder().build())
                    .build())
        .isInstanceOf(MissingMandatoryFieldException.class);
  }
}
