package znu.visum.components.genres.usecases.deprecated.update.application;

import znu.visum.components.genres.domain.models.Genre;

public class UpdateGenreResponse {
  private Long id;

  private String type;

  public UpdateGenreResponse(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static UpdateGenreResponse from(Genre genre) {
    return new UpdateGenreResponse(genre.getId(), genre.getType());
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
