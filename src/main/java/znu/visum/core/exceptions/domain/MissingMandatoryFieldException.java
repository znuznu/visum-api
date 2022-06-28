package znu.visum.core.exceptions.domain;

public class MissingMandatoryFieldException extends VisumException {

  private MissingMandatoryFieldException(String field) {
    super(
        "Missing mandatory field " + field,
        VisumExceptionStatus.INTERNAL_SERVER_ERROR,
        "MISSING_MANDATORY_FIELD");
  }

  public static MissingMandatoryFieldException forNullValue(String field) {
    return new MissingMandatoryFieldException(field);
  }
}
