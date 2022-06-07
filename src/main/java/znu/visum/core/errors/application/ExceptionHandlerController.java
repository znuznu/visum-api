package znu.visum.core.errors.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import znu.visum.core.errors.domain.InternalException;
import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

  private static final String INTERNAL_ERROR_MESSAGE = "Something went wrong";

  @ExceptionHandler({Exception.class})
  public ResponseEntity<HttpException> handleNoneVisumException(
      Exception exception, WebRequest request) {
    log.error("Unexpected error", exception);

    return HttpException.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .code("500")
        .message(INTERNAL_ERROR_MESSAGE)
        .error(VisumExceptionStatus.INTERNAL_SERVER_ERROR.getRepresentation())
        .path(request.getDescription(false).substring(4))
        .build()
        .toResponseEntity();
  }

  @ExceptionHandler({VisumException.class})
  public ResponseEntity<HttpException> handleVisumException(
      VisumException exception, WebRequest request) {
    log.error(exception.getMessage());

    // Hide message before sending it to the client
    String message =
        (exception instanceof InternalException) ? INTERNAL_ERROR_MESSAGE : exception.getMessage();

    return HttpException.builder()
        .status(getHttpStatus(exception))
        .code(exception.getCode())
        .message(message)
        .error(exception.getStatus().getRepresentation())
        .path(request.getDescription(false).substring(4))
        .build()
        .toResponseEntity();
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<HttpException> handleInvalidBody(Exception exception, WebRequest request) {
    log.error(exception.getMessage());

    return HttpException.builder()
        .code("INVALID_BODY")
        .message("Invalid body.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build()
        .toResponseEntity();
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
  public ResponseEntity<HttpException> handleInvalidArgument(
      Exception exception, WebRequest request) {
    log.error(exception.getMessage());

    return HttpException.builder()
        .code("INVALID_ARGUMENT")
        .message("Invalid argument.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build()
        .toResponseEntity();
  }

  private HttpStatus getHttpStatus(VisumException exception) {
    VisumExceptionStatus status = exception.getStatus();
    if (status == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    switch (status) {
      case BAD_REQUEST:
        return HttpStatus.BAD_REQUEST;
      case UNAUTHORIZED:
        return HttpStatus.UNAUTHORIZED;
      case FORBIDDEN:
        return HttpStatus.FORBIDDEN;
      case NOT_FOUND:
        return HttpStatus.NOT_FOUND;
      case INTERNAL_SERVER_ERROR:
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
