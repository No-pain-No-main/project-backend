package com.adanext.NoPainNoMain.config;

public final class JwtParameters {

    private JwtParameters() {
       
    }

    public static final String SECRET = "OWQyNmE0YjZjOGZhM2I1YjQ0ZTVhMmMxZjliODc3ZDZhNGQwYjA1M2U4ZmI3YzZhNDg5YzFhZmI5ZjA2YzZlNQ==";

    public static final long STUDENT_EXPIRATION_MS = 3_600_000L;

    public static final long ADMIN_EXPIRATION_MS = 28_800_000L;

    public static final long VALIDATOR_EXPIRATION_MS = 28_800_000L;

    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_VALIDATOR = "VALIDATOR";

    public static final String AUTH_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final int BEARER_PREFIX_LENGTH = 7;

    public static final String ROLE_PREFIX = "ROLE_";

    public static final int MAX_FAILED_ATTEMPTS = 4;

    public static final long LOCKOUT_DURATION_MS = 300_000L;
}