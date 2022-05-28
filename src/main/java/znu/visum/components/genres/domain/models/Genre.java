package znu.visum.components.genres.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Genre {

  private Long id;
  private String type;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Genre)) return false;
    Genre genre = (Genre) o;
    return Objects.equals(getId(), genre.getId()) && Objects.equals(getType(), genre.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType());
  }
}
