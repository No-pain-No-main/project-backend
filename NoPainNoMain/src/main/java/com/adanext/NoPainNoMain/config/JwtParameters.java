package com.adanext.NoPainNoMain.config;

/**
 * Parámetros configurables para la autenticación JWT.
 */
public final class JwtParameters {

    private JwtParameters() {
        // Clase de constantes, no instanciable
    }

    /** Clave secreta HMAC-SHA256 en Base64 */
    public static final String SECRET = "OWQyNmE0YjZjOGZhM2I1YjQ0ZTVhMmMxZjliODc3ZDZhNGQwYjA1M2U4ZmI3YzZhNDg5YzFhZmI5ZjA2YzZlNQ==";

    /** Tiempo de expiración del token para estudiantes (1 hora) */
    public static final long STUDENT_EXPIRATION_MS = 3_600_000L;

    /** Tiempo de expiración del token para administradores (8 horas) */
    public static final long ADMIN_EXPIRATION_MS = 28_800_000L;

    /** Tiempo de expiración del token para validadores (8 horas) */
    public static final long VALIDATOR_EXPIRATION_MS = 28_800_000L;

    // ─── Roles ─────────────────────────────────────────────────────
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_VALIDATOR = "VALIDATOR";

    // ─── Token ────────────────────────────────────────────────────
    /** Nombre del header HTTP que contiene el token JWT */
    public static final String AUTH_HEADER = "Authorization";

    /** Prefijo del valor del header antes del token JWT */
    public static final String BEARER_PREFIX = "Bearer ";

    /** Longitud del prefijo "Bearer " (7 caracteres) */
    public static final int BEARER_PREFIX_LENGTH = 7;

    /** Prefijo para roles en Spring Security */
    public static final String ROLE_PREFIX = "ROLE_";

    // ─── Autenticación ─────────────────────────────────────────────
    /** Máximo de intentos fallidos antes de bloquear */
    public static final int MAX_FAILED_ATTEMPTS = 4;

    /** Duración del bloqueo en milisegundos (5 minutos) */
    public static final long LOCKOUT_DURATION_MS = 300_000L;
}