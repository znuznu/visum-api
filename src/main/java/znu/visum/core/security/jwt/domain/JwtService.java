package znu.visum.core.security.jwt.domain;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {

  String HEADER_PREFIX = "Bearer ";

  String createToken(JwtCreationCommand command);

  String verifyAndGetSubject(JwtVerifyQuery query);
}
