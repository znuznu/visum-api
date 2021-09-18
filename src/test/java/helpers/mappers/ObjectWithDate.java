package helpers.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectWithDate {
  private LocalDate localDate;

  private LocalDateTime localDateTime;

  public ObjectWithDate(LocalDate localDate, LocalDateTime localDateTime) {
    this.localDate = localDate;
    this.localDateTime = localDateTime;
  }

  public LocalDate getLocalDate() {
    return localDate;
  }

  public void setLocalDate(LocalDate localDate) {
    this.localDate = localDate;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }
}
