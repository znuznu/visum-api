package znu.visum.core.security.jwt.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;
import znu.visum.core.security.jwt.domain.InvalidTokenException;
import znu.visum.core.security.jwt.domain.JwtCreationCommand;
import znu.visum.core.security.jwt.domain.JwtVerifyQuery;

import java.time.Instant;

class Auth0JwtServiceUnitTest {

  @Nested
  class CreateToken {

    private Auth0JwtService service;

    @BeforeEach
    void setup() {
      service = new Auth0JwtService();
    }

    @Test
    void shouldCreateToken() {
      Assertions.assertThat(service.createToken(command())).isNotBlank();
    }

    @Test
    void shouldNotCreateWithNull() {
      Assertions.assertThatThrownBy(() -> service.createToken(null))
          .isInstanceOf(MissingMandatoryFieldException.class);
    }

    private JwtCreationCommand command() {
      return new JwtCreationCommand("secret_key", "Saul Goodman", Instant.MAX);
    }
  }

  @Nested
  class VerifyToken {

    private Auth0JwtService service;

    @BeforeEach
    void setup() {
      service = new Auth0JwtService();
    }

    @Test
    void shouldThrowOnNullToken() {
      Assertions.assertThatThrownBy(() -> service.verifyAndGetSubject(null))
          .isInstanceOf(MissingMandatoryFieldException.class);
    }

    @Test
    void shouldThrowOnInvalidToken() {
      Assertions.assertThatThrownBy(
              () ->
                  service.verifyAndGetSubject(new JwtVerifyQuery("secret_key", "woaw.fake.token")))
          .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void shouldReturnSubject() {
      var subject = service.verifyAndGetSubject(query());

      Assertions.assertThat(subject).isEqualTo("Saul Goodman");
    }

    private JwtVerifyQuery query() {
      return new JwtVerifyQuery(
          "secret_key",
          "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYXVsIEdvb2RtYW4iLCJleHAiOjMxNTU2ODg5ODY0NDAzMTk5fQ._uEYs0CQzJ7jgDZzC6xuo4fhTI9T6574Zy25sPgm9LJOCfddxL7HRNNpq78L8ye4hyDHhYLMK7L7TdYfln8VNA");
    }
  }
}
