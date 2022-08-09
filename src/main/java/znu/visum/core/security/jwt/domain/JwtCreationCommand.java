package znu.visum.core.security.jwt.domain;


import znu.visum.core.assertions.VisumAssert;

import java.time.Instant;

public record JwtCreationCommand(String secretKey, String subject, Instant expiresAt) {

    public JwtCreationCommand {
        VisumAssert.notNull("secretKey", secretKey);
        VisumAssert.notNull("subject", subject);
        VisumAssert.notNull("expiresAt", expiresAt);
    }
}
