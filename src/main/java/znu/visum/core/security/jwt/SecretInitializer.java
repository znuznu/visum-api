package znu.visum.core.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretInitializer {
  private final String jwtSecretKey;
  private final String allowedOrigins;

  public SecretInitializer(
      @Value("visum.jwt-secret-key") String jwtSecretKey,
      @Value("${visum.allowed-origins}") String allowedOrigins) {
    this.jwtSecretKey = jwtSecretKey;
    this.allowedOrigins = allowedOrigins;
  }

  public String getJwtSecretKey() {
    return jwtSecretKey;
  }

  public String getAllowedOrigins() {
    return allowedOrigins;
  }
}
