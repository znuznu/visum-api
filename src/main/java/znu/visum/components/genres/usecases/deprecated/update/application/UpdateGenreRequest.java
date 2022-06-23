package znu.visum.components.genres.usecases.deprecated.update.application;

import znu.visum.components.genres.domain.Genre;

public class UpdateGenreRequest {
  private Long id;

  private String type;

  public UpdateGenreRequest(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public Genre toDomain() {
    return new Genre(this.getId(), this.getType());
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
}
