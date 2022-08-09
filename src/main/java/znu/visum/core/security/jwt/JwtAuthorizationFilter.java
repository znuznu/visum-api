package znu.visum.core.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import znu.visum.core.security.jwt.domain.JwtService;
import znu.visum.core.security.jwt.domain.JwtVerifyQuery;
import znu.visum.core.security.jwt.domain.MissingTokenHeaderException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtService jwtService;

  private final String jwtSecretKey;

  public JwtAuthorizationFilter(
      String jwtSecretKey, AuthenticationManager authManager, JwtService jwtService) {
    super(authManager);
    this.jwtService = jwtService;
    this.jwtSecretKey = jwtSecretKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader("Authorization");
    if (header == null || !header.startsWith(JwtService.HEADER_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null) {
      throw new MissingTokenHeaderException();
    }

    String token = authorizationHeader.replace(JwtService.HEADER_PREFIX, "");

    var query = new JwtVerifyQuery(this.jwtSecretKey, token);
    String subject = this.jwtService.verifyAndGetSubject(query);

    return new UsernamePasswordAuthenticationToken(subject, null, new ArrayList<>());
  }
}
