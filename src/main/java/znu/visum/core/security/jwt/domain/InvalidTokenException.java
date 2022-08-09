package znu.visum.core.security.jwt.domain;

public class InvalidTokenException extends UnauthorizedException {

  public InvalidTokenException() {
    super("Invalid JWT token.", "INVALID_JWT_TOKEN");
  }
}
