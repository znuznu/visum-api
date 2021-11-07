package znu.visum.core.errors.application;

public class ControllerException extends RuntimeException {
  private final ExceptionResponse body;

  public ControllerException(ExceptionResponse body) {
    super(body.getMessage());
    this.body = body;
  }

  public ExceptionResponse getExceptionEntity() {
    return body;
  }
}
