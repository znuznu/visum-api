package znu.visum.components.diary.domain;

import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class InvalidDiaryMovieEntryDate extends VisumException {

  private InvalidDiaryMovieEntryDate(String message) {
    super(message, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "INVALID_DIARY_MOVIE_DATE");
  }

  public static InvalidDiaryMovieEntryDate of(Month month, LocalDate viewingDate) {
    return new InvalidDiaryMovieEntryDate(
        "Could not add entry with viewing date "
            + viewingDate.format(DateTimeFormatter.ISO_DATE)
            + " to a "
            + month
            + " diary month.");
  }
}
