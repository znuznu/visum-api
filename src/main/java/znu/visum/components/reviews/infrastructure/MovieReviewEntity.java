package znu.visum.components.reviews.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.movies.infrastructure.MovieEntity;
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

  public static MovieReviewEntity from(ReviewFromMovie reviewFromMovie) {
    return MovieReviewEntity.builder()
        .movieEntity(MovieEntity.builder().id(reviewFromMovie.getId()).build())
        .id(reviewFromMovie.getId())
        .creationDate(reviewFromMovie.getCreationDate())
        .updateDate(reviewFromMovie.getUpdateDate())
        .grade(reviewFromMovie.getGrade().getValue())
        .content(reviewFromMovie.getContent())
        .build();
  }

  public static MovieReviewEntity from(Review review) {
    return MovieReviewEntity.builder()
        .id(review.getId())
        .grade(review.getGrade().getValue())
        .content(review.getContent())
        .movieEntity(MovieEntity.from(review.getMovie()))
        .creationDate(review.getCreationDate())
        .updateDate(review.getUpdateDate())
        .build();
  }

  public Review toDomain() {
    return Review.builder()
        .id(this.id)
        .content(this.content)
        .updateDate(this.updateDate)
        .creationDate(this.creationDate)
        .grade(new Grade(this.grade))
        .movie(this.movieEntity == null ? null : this.movieEntity.toMovieFromReview())
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
