package znu.visum.core.errors.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import znu.visum.core.errors.domain.VisumException;
import znu.visum.core.errors.domain.VisumExceptionStatus;

import javax.validation.ConstraintViolationException;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private static final String INTERNAL_ERROR_MESSAGE = "Something went wrong";

  Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpExceptionResponse handleNoneVisumException(Exception exception, WebRequest request) {
    logger.error("Unexpected error", exception);

    return HttpExceptionResponse.builder()
        .code("500")
        .error(VisumExceptionStatus.INTERNAL_SERVER_ERROR.getRepresentation())
        .message(INTERNAL_ERROR_MESSAGE)
        .path(request.getDescription(false).substring(4))
        .status(resolveAnnotatedResponseStatus(exception))
        .build();
  }

  @ExceptionHandler({VisumException.class})
  public ResponseEntity<HttpExceptionResponse> handleVisumException(
      VisumException exception, WebRequest request) {
    logger.error(exception.getMessage());

    // Hide message before sending it to the client
    String message =
        exception.getStatus() == VisumExceptionStatus.INTERNAL_SERVER_ERROR
            ? INTERNAL_ERROR_MESSAGE
            : exception.getMessage();

    return new ResponseEntity<>(
        HttpExceptionResponse.builder()
            .status(getHttpStatus(exception))
            .code(exception.getCode())
            .message(message)
            .error(exception.getStatus().getRepresentation())
            .path(request.getDescription(false).substring(4))
            .build(),
        getHttpStatus(exception));
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpExceptionResponse handleHttpMessageNotReadableException(
      Exception exception, WebRequest request) {

    logger.error(exception.getMessage());

    return HttpExceptionResponse.builder()
        .code("INVALID_BODY")
        .message("Invalid body.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpExceptionResponse handleMethodArgumentTypeMismatchException(
      Exception exception, WebRequest request) {

    logger.error(exception.getMessage());

    return HttpExceptionResponse.builder()
        .code("INVALID_ARGUMENT")
        .message("Invalid argument.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpExceptionResponse handleConstraintViolationException(
      Exception exception, WebRequest request) {

    logger.error(exception.getMessage());

    return HttpExceptionResponse.builder()
        .code("INVALID_ARGUMENT")
        .message("Invalid argument.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpExceptionResponse handleBadRequest(Exception exception, WebRequest request) {
    logger.error(exception.getMessage());

    return HttpExceptionResponse.builder()
        .code("INVALID_BODY")
        .message("Invalid body.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  private HttpStatus resolveAnnotatedResponseStatus(Throwable t) {
    ResponseStatus status = findMergedAnnotation(t.getClass(), ResponseStatus.class);
    return status != null ? status.value() : null;
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
