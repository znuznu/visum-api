package znu.visum.core.errors.domain;

public class ExternalInconsistencyException extends VisumException {

  public ExternalInconsistencyException(String message) {
    super(message, VisumExceptionStatus.INTERNAL_SERVER_ERROR, "INCONSISTENT_EXTERNAL_RESPONSE");
  }
}
