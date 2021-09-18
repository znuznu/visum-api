package znu.visum.components.genres.infrastructure.models;

import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.movies.infrastructure.models.MovieEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
public class GenreEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_seq")
  private Long id;

  private String type;

  @ManyToMany
  @JoinTable(
      name = "movie_genre",
      joinColumns = @JoinColumn(name = "genre_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<MovieEntity> movieEntities;

  public GenreEntity() {}

  public static GenreEntity from(Genre genre) {
    return new GenreEntity().id(genre.getId()).type(genre.getType());
  }

  public Genre toDomain() {
    return new Genre(this.id, this.type);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GenreEntity id(Long id) {
    this.id = id;

    return this;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public GenreEntity type(String type) {
    this.type = type;

    return this;
  }

  public Set<MovieEntity> getMovies() {
    return movieEntities;
  }

  public void setMovies(Set<MovieEntity> movieEntities) {
    this.movieEntities = movieEntities;
  }
}
