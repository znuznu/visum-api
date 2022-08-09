package znu.visum.core.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import znu.visum.components.accounts.infrastructure.AccountEntity;
import znu.visum.core.security.jwt.domain.JwtCreationCommand;
import znu.visum.core.security.jwt.domain.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final String SIGN_IN_PATH = "/api/accounts/sign-in";

  private final JwtService jwtService;

  private final String jwtSecretKey;

  public JwtAuthenticationFilter(
      String jwtSecretKey, AuthenticationManager authenticationManager, JwtService jwtService) {
    super(authenticationManager);
    this.jwtService = jwtService;
    this.jwtSecretKey = jwtSecretKey;
    setFilterProcessesUrl(SIGN_IN_PATH);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {
    try {
      AccountEntity accountEntity =
          new ObjectMapper().readValue(req.getInputStream(), AccountEntity.class);

      return this.getAuthenticationManager()
          .authenticate(
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
    var command =
        new JwtCreationCommand(
            this.jwtSecretKey,
            ((User) auth.getPrincipal()).getUsername(),
            Instant.now().plusMillis(6_000_000));

    String token = this.jwtService.createToken(command);

    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(String.format("{\"token\":\"%s\"}", token));
  }
}
