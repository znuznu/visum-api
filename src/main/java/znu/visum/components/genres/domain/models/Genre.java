package znu.visum.components.genres.domain.models;

import java.util.Objects;

public class Genre {
  private Long id;

  private String type;

  public Genre(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

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
