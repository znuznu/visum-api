package znu.visum.components.history.infrastructure.models;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class ViewingHistoryEntity {
  protected LocalDate viewingDate;

  public ViewingHistoryEntity() {}

  public LocalDate getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(LocalDate viewingDate) {
    this.viewingDate = viewingDate;
  }

  public ViewingHistoryEntity viewingDate(LocalDate viewingDate) {
    this.viewingDate = viewingDate;

    return this;
  }
}
