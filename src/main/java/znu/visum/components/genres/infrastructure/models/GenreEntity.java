package znu.visum.components.genres.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.movies.infrastructure.models.MovieEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

  public static GenreEntity from(Genre genre) {
    return new GenreEntity(genre.getId(), genre.getType(), null);
  }

  public Genre toDomain() {
    return new Genre(this.id, this.type);
  }

  public Set<MovieEntity> getMovies() {
    return movieEntities;
  }

  public void setMovies(Set<MovieEntity> movieEntities) {
    this.movieEntities = movieEntities;
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

    GenreEntity other = (GenreEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
