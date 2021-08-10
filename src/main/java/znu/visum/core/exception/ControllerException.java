package znu.visum.core.exception;

public class ControllerException extends RuntimeException {
    private final ExceptionEntity body;

    public ControllerException(ExceptionEntity body) {
        super(body.getMessage());
        this.body = body;
    }

    public ExceptionEntity getExceptionEntity() {
        return body;
    }
}
