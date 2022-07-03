package znu.visum.components.movies.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/** Represents a composite Primary Key related to the cast_member table. */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CastMemberId implements Serializable {

  @Column(name = "movie_id")
  private Long movieId;

  @Column(name = "actor_id")
  private Long actorId;

  @Column(name = "character")
  private String character;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CastMemberId other = (CastMemberId) o;
    return Objects.equals(movieId, other.movieId)
        && Objects.equals(actorId, other.actorId)
        && Objects.equals(character, other.character);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movieId, actorId, character);
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  public void setActorId(Long actorId) {
    this.actorId = actorId;
  }

  public void setCharacter(String character) {
    this.character = character;
  }
}
