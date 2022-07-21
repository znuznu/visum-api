package znu.visum.core.exceptions.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import znu.visum.core.exceptions.domain.InternalException;
import znu.visum.core.exceptions.domain.VisumException;
import znu.visum.core.exceptions.domain.VisumExceptionStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HttpException> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception, WebRequest request) {
    log.error(exception.getMessage());

    List<String> errors =
        exception.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.toList());

    return HttpException.builder()
        .code("INVALID_BODY")
        .message(String.join(",", errors))
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build()
        .toResponseEntity();
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<HttpException> handleHttpMessageNotReadableException(
      Exception exception, WebRequest request) {
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

    return switch (status) {
      case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
      case FORBIDDEN -> HttpStatus.FORBIDDEN;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }
}
