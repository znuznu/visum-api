package znu.visum.core.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class ExceptionEntity {
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    @JsonIgnore
    @Nullable
    private HttpStatus status;

    private String code;

    private String error;

    private String path;

    @JsonIgnore
    private HttpHeaders headers;

    public ExceptionEntity(ExceptionEntity other) {
        this.timestamp = other.timestamp;
        this.message = other.message;
        this.status = other.status;
        this.code = other.code;
        this.error = other.error;
        this.path = other.path;
        this.headers = other.headers;
    }

    public ExceptionEntity() {
    }

    public ResponseEntity<ExceptionEntity> toResponseEntity() {
        return new ResponseEntity<>(this, headers, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(@Nullable HttpStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public static final class Builder {
        private final ExceptionEntity exceptionEntity;

        public Builder() {
            exceptionEntity = new ExceptionEntity();
        }

        public Builder timestamp(LocalDateTime timestamp) {
            exceptionEntity.setTimestamp(timestamp);
            return this;
        }

        public Builder message(String message) {
            exceptionEntity.setMessage(message);
            return this;
        }

        public Builder status(HttpStatus status) {
            exceptionEntity.setStatus(status);
            return this;
        }

        public Builder code(String code) {
            exceptionEntity.setCode(code);
            return this;
        }

        public Builder error(String error) {
            exceptionEntity.setError(error);
            return this;
        }

        public Builder path(String path) {
            exceptionEntity.setPath(path);
            return this;
        }

        public ExceptionEntity build() {
            return new ExceptionEntity(exceptionEntity);
        }
    }
}
