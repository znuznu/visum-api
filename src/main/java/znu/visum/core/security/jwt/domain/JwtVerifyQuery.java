package znu.visum.core.security.jwt.domain;

import znu.visum.core.assertions.VisumAssert;

public record JwtVerifyQuery(String secretKey, String token) {

    public JwtVerifyQuery {
        VisumAssert.notNull("secretKey", secretKey);
        VisumAssert.notNull("token", token);
    }
}
