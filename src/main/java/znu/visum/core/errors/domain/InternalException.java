package znu.visum.core.errors.domain;

public class InternalException extends VisumException {

  private InternalException(String message) {
    super(message, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "500");
  }

  public static InternalException withMessage(String message) {
    return new InternalException(message);
  }
}
