package znu.visum.core.exceptions.domain;

public class InvalidStringException extends VisumException {

  private InvalidStringException(String field, String message) {
    super(message + field, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "INVALID_STRING_EXCEPTION");
  }

  public static InvalidStringException forBlankValue(String field) {
    return new InvalidStringException(field, "Blank field ");
  }
}
