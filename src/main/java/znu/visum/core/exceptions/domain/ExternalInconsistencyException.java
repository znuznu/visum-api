package znu.visum.core.exceptions.domain;

public class ExternalInconsistencyException extends VisumException {

  private ExternalInconsistencyException(String message) {
    super(message, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "INCONSISTENT_EXTERNAL_RESPONSE");
  }

  public static ExternalInconsistencyException withMessage(String message) {
    return new ExternalInconsistencyException(message);
  }
}
