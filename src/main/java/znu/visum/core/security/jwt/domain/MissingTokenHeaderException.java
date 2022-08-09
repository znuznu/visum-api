package znu.visum.core.security.jwt.domain;

public class MissingTokenHeaderException extends UnauthorizedException {

  public MissingTokenHeaderException() {
    super("Missing token header.", "MISSING_TOKEN_HEADER");
  }
}
