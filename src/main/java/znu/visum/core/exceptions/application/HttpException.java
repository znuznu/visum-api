package znu.visum.core.exceptions.application;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
@Builder
public class HttpException {

  HttpStatus status;
  String code;
  String message;
  String error;
  String path;

  public ResponseEntity<HttpException> toResponseEntity() {
    return new ResponseEntity<>(this, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
