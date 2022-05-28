package znu.visum.components.history.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@SuperBuilder
@Getter
public abstract class ViewingHistoryEntity {

  private LocalDate viewingDate;

  public ViewingHistoryEntity() {}
}
