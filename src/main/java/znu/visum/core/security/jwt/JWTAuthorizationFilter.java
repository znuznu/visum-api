package znu.visum.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private final String jwtSecretKey;

  public JWTAuthorizationFilter(String jwtSecretKey, AuthenticationManager authManager) {
    super(authManager);
    this.jwtSecretKey = jwtSecretKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null) {
      // TODO throw proper exception
      throw new RuntimeException("Invalid header.");
    }

    String user =
        JWT.require(Algorithm.HMAC512(this.jwtSecretKey.getBytes()))
            .build()
            .verify(token.replace("Bearer ", ""))
            .getSubject();

    if (user == null) {
      throw new RuntimeException("Invalid token.");
    }

    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }
}
