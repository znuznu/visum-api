package znu.visum.core.errors.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import znu.visum.components.accounts.domain.errors.InvalidRegistrationKeyException;
import znu.visum.components.accounts.domain.errors.MaximumAccountReachedException;
import znu.visum.components.external.domain.errors.NoSuchExternalMovieIdException;
import znu.visum.components.genres.domain.errors.GenreAlreadyExistsException;
import znu.visum.components.genres.domain.errors.NoSuchGenreIdException;
import znu.visum.components.history.domain.errors.NoSuchViewingHistoryException;
import znu.visum.components.movies.domain.errors.MovieAlreadyExistsException;
import znu.visum.components.movies.domain.errors.NoSuchMovieIdException;
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;
import znu.visum.components.reviews.domain.errors.MaximumMovieReviewsReachedException;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler({ControllerException.class})
  public ResponseEntity<ExceptionResponse> handleControllerException(ControllerException e) {
    e.printStackTrace();

    return e.getExceptionEntity().toResponseEntity();
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleInternalError(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("500")
        .error("Internal Server Error")
        .message("Oops. Something went wrong!")
        .path(request.getDescription(false).substring(4))
        .status(resolveAnnotatedResponseStatus(e))
        .build();
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleHttpMessageNotReadableException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("INVALID_BODY")
        .message("Invalid body.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleMethodArgumentTypeMismatchException(
      Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("INVALID_ARGUMENT")
        .message("Invalid argument.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleConstraintViolationException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("INVALID_ARGUMENT")
        .message("Invalid argument.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleBadRequest(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("INVALID_BODY")
        .message("Invalid body.")
        .error("Bad Request")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler({
    NoSuchElementException.class,
    NoSuchFieldException.class,
    NoSuchActorIdException.class,
    NoSuchDirectorIdException.class,
    NoSuchGenreIdException.class,
    NoSuchReviewIdException.class,
    NoSuchMovieIdException.class,
    NoSuchViewingHistoryException.class,
    NoSuchExternalMovieIdException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleCommonNotFound(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .code("RESOURCE_NOT_FOUND")
        .message(e.getMessage())
        .error("Not Found")
        .path(request.getDescription(false).substring(4))
        .status(HttpStatus.NOT_FOUND)
        .build();
  }

  @ExceptionHandler({GenreAlreadyExistsException.class, MovieAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleResourceAlreadyExistsException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .status(HttpStatus.BAD_REQUEST)
        .code("DATA_ALREADY_EXISTS")
        .error("Bad Request")
        .message(e.getMessage())
        .path(request.getDescription(false).substring(4))
        .build();
  }

  @ExceptionHandler(MaximumMovieReviewsReachedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleMovieAlreadyHaveAReviewException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .status(HttpStatus.BAD_REQUEST)
        .code("MAXIMUM_NUMBER_OF_REVIEWS_REACHED")
        .error("Bad Request")
        .message(e.getMessage())
        .path(request.getDescription(false).substring(4))
        .build();
  }

  @ExceptionHandler(InvalidRegistrationKeyException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionResponse handleInvalidRegistrationKeyException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .status(HttpStatus.UNAUTHORIZED)
        .code("INVALID_REGISTRATION_KEY")
        .error("Unauthorized")
        .message(e.getMessage())
        .path(request.getDescription(false).substring(4))
        .build();
  }

  @ExceptionHandler(MaximumAccountReachedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ExceptionResponse handleMaximumAccountReachedException(Exception e, WebRequest request) {
    e.printStackTrace();

    return new ExceptionResponse.Builder()
        .status(HttpStatus.FORBIDDEN)
        .code("MAXIMUM_NUMBER_OF_ACCOUNT_REACHED")
        .path(request.getDescription(false).substring(4))
        .message(e.getMessage())
        .error("Forbidden")
        .build();
  }

  private HttpStatus resolveAnnotatedResponseStatus(Throwable t) {
    ResponseStatus status = findMergedAnnotation(t.getClass(), ResponseStatus.class);
    return status != null ? status.value() : null;
  }
}
