package znu.visum.core.security.jwt.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import znu.visum.core.assertions.VisumAssert;
import znu.visum.core.security.jwt.domain.InvalidTokenException;
import znu.visum.core.security.jwt.domain.JwtCreationCommand;
import znu.visum.core.security.jwt.domain.JwtService;
import znu.visum.core.security.jwt.domain.JwtVerifyQuery;

public class Auth0JwtService implements JwtService {

  @Override
  public String createToken(JwtCreationCommand command) {
    VisumAssert.notNull("command", command);

    return JWT.create()
        .withSubject(command.subject())
        .withExpiresAt(command.expiresAt())
        .sign(Algorithm.HMAC512(command.secretKey().getBytes()));
  }

  @Override
  public String verifyAndGetSubject(JwtVerifyQuery query) throws InvalidTokenException {
    VisumAssert.notNull("query", query);

    try {
      return JWT.require(Algorithm.HMAC512(query.secretKey().getBytes()))
          .build()
          .verify(query.token())
          .getSubject();
    } catch (JWTVerificationException e) {
      throw new InvalidTokenException();
    }
  }
}
