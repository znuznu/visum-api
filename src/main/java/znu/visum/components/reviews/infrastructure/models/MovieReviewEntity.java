package znu.visum.components.reviews.infrastructure.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import znu.visum.components.movies.domain.models.ReviewFromMovie;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.reviews.domain.models.Review;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_review")
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

  public MovieReviewEntity() {}

  public static MovieReviewEntity from(ReviewFromMovie reviewFromMovie) {
    return new MovieReviewEntity.Builder()
        .movieEntity(null)
        .id(reviewFromMovie.getId())
        .creationDate(reviewFromMovie.getCreationDate())
        .updateDate(reviewFromMovie.getUpdateDate())
        .grade(reviewFromMovie.getGrade())
        .content(reviewFromMovie.getContent())
        .build();
  }

  public static MovieReviewEntity from(Review review) {
    return new MovieReviewEntity.Builder()
        .id(review.getId())
        .grade(review.getGrade())
        .content(review.getContent())
        .movieEntity(MovieEntity.from(review.getMovie()))
        .creationDate(review.getCreationDate())
        .updateDate(review.getUpdateDate())
        .build();
  }

  public MovieReviewEntity movie(MovieEntity movieEntity) {
    this.movieEntity = movieEntity;

    return this;
  }

  public Review toDomain() {
    return new Review(
        this.id,
        this.content,
        this.updateDate,
        this.creationDate,
        this.grade,
        this.movieEntity == null ? null : this.movieEntity.toMovieFromReview());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MovieEntity getMovieEntity() {
    return movieEntity;
  }

  public void setMovieEntity(MovieEntity movieEntity) {
    this.movieEntity = movieEntity;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static final class Builder {
    private final MovieReviewEntity movieReviewEntity;

    private Builder() {
      movieReviewEntity = new MovieReviewEntity();
    }

    public Builder id(Long id) {
      movieReviewEntity.setId(id);
      return this;
    }

    public Builder grade(int grade) {
      movieReviewEntity.setGrade(grade);
      return this;
    }

    public Builder creationDate(LocalDateTime creationDate) {
      movieReviewEntity.setCreationDate(creationDate);
      return this;
    }

    public Builder updateDate(LocalDateTime updateDate) {
      movieReviewEntity.setUpdateDate(updateDate);
      return this;
    }

    public Builder content(String content) {
      movieReviewEntity.setContent(content);
      return this;
    }

    public Builder movieEntity(MovieEntity movie) {
      movieReviewEntity.setMovieEntity(movie);
      return this;
    }

    public MovieReviewEntity build() {
      return movieReviewEntity;
    }
  }
}
