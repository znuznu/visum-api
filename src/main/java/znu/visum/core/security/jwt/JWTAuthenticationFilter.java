package znu.visum.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import znu.visum.components.accounts.infrastructure.models.AccountEntity;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final String jwtSecretKey;

  public JWTAuthenticationFilter(String jwtSecretKey, AuthenticationManager authenticationManager) {
    this.jwtSecretKey = jwtSecretKey;
    this.authenticationManager = authenticationManager;
    setFilterProcessesUrl("/api/accounts/sign-in");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {
    try {
      AccountEntity accountEntity =
          new ObjectMapper().readValue(req.getInputStream(), AccountEntity.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              accountEntity.getUsername(), accountEntity.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException {
    String token =
        JWT.create()
            .withSubject(((User) auth.getPrincipal()).getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 6_000_000))
            .sign(Algorithm.HMAC512(jwtSecretKey.getBytes()));

    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(String.format("{\"%s\":\"%s\"}", "token", token));
  }
}
