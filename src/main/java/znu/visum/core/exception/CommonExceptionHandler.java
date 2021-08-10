package znu.visum.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;


@RestControllerAdvice
public class CommonExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler({ControllerException.class})
    public ResponseEntity<ExceptionEntity> handleControllerException(ControllerException e) {
        e.printStackTrace();

        return e.getExceptionEntity().toResponseEntity();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionEntity handleInternalError(Exception e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .code("500")
                .message("Oops. Something bad happened!")
                .error("Internal Server Error")
                .path(request.getDescription(false).substring(4))
                .status(resolveAnnotatedResponseStatus(e))
                .build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionEntity handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .code("400")
                .message("Body syntactically invalid.")
                .error("Bad Request")
                .path(request.getDescription(false).substring(4))
                .status(resolveAnnotatedResponseStatus(e))
                .build();
    }

    @ExceptionHandler({NoSuchElementException.class, NoSuchFieldException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionEntity handleNotFound(Exception e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .code("404")
                .message("The data can't be found.")
                .error("Not Found")
                .path(request.getDescription(false).substring(4))
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionEntity handleBadRequest(Exception e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .code("400")
                .message("The argument provided is invalid.")
                .error("Bad Request")
                .path(request.getDescription(false).substring(4))
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionEntity handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .code("400")
                .message("The data already exists.")
                .error("Bad Request")
                .path(request.getDescription(false).substring(4))
                .build();
    }

    private HttpStatus resolveAnnotatedResponseStatus(Throwable t) {
        ResponseStatus status = findMergedAnnotation(t.getClass(), ResponseStatus.class);
        return status != null ? status.value() : null;
    }
}
