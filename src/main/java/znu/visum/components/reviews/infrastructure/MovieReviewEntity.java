package znu.visum.components.reviews.infrastructure;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.movies.infrastructure.MovieEntity;
import znu.visum.components.reviews.domain.Content;
import znu.visum.components.reviews.domain.Grade;
import znu.visum.components.reviews.domain.Review;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MovieReviewEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_review_id_seq")
  private Long id;

  @OneToOne
  @JoinColumn(name = "movie_id")
  private MovieEntity movieEntity;

  private int grade;

  @CreationTimestamp private LocalDateTime creationDate;

  @UpdateTimestamp private LocalDateTime updateDate;

  private String content;

  public static MovieReviewEntity ofNullable(ReviewFromMovie review) {
    if (review == null) {
      return null;
    }

    return MovieReviewEntity.builder()
        .movieEntity(MovieEntity.builder().id(review.getId()).build())
        .id(review.getId())
        .creationDate(review.getCreationDate())
        .updateDate(review.getUpdateDate())
        .grade(review.getGrade().getValue())
        .content(review.getContent())
        .build();
  }

  public static MovieReviewEntity ofNullable(Review review) {
    return MovieReviewEntity.builder()
        .id(review.getId())
        .grade(review.getGrade().getValue())
        .content(review.getContent().getText())
        .movieEntity(MovieEntity.from(review.getMovie()))
        .creationDate(review.getCreationDate())
        .updateDate(review.getUpdateDate())
        .build();
  }

  public Review toDomain() {
    return Review.builder()
        .id(this.id)
        .grade(Grade.of(this.grade))
        .content(Content.of(this.content))
        .movie(this.movieEntity == null ? null : this.movieEntity.toMovieFromReview())
        .updateDate(this.updateDate)
        .creationDate(this.creationDate)
        .build();
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    MovieReviewEntity other = (MovieReviewEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
