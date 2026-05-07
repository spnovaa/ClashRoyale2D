package clashroyale.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

/**
 * BCrypt-backed password hashing.
 *
 * <p>The legacy login flow compared plaintext passwords directly via
 * {@code resultSet.getString("password").equals(submitted)} — both
 * insecure and trivially broken by a database leak. This helper
 * provides one-way hashing (with a per-password salt and a configurable
 * work-factor) plus a constant-time verifier.</p>
 *
 * <p>{@link #verify(String, String)} also recognises legacy plaintext
 * rows so existing user accounts keep working: callers should re-hash
 * and persist on the next successful login (see {@code AuthenticationService}).</p>
 */
public final class PasswordHasher {

    /** BCrypt work factor — 12 ≈ 250 ms on a modern CPU; tune as needed. */
    private static final int COST = 12;

    private PasswordHasher() {}

    public static String hash(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("password must not be null");
        }
        return BCrypt.withDefaults()
                .hashToString(COST, rawPassword.toCharArray());
    }

    /**
     * @param rawPassword candidate password (plain)
     * @param storedHash  the value as read from the {@code users.password} column
     * @return true when the password matches, including the legacy plain-text case
     */
    public static boolean verify(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null) return false;
        if (!isBcrypt(storedHash)) {
            // Legacy plaintext row — keep returning true so old accounts log in,
            // but callers should immediately upgrade the row to a real hash.
            return constantTimeEquals(rawPassword, storedHash);
        }
        BCrypt.Result result = BCrypt.verifyer()
                .verify(rawPassword.toCharArray(), storedHash.toCharArray());
        return result.verified;
    }

    public static boolean isBcrypt(String stored) {
        return stored != null
                && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"));
    }

    private static boolean constantTimeEquals(String a, String b) {
        byte[] x = a.getBytes(StandardCharsets.UTF_8);
        byte[] y = b.getBytes(StandardCharsets.UTF_8);
        int diff = x.length ^ y.length;
        for (int i = 0; i < Math.min(x.length, y.length); i++) {
            diff |= x[i] ^ y[i];
        }
        return diff == 0;
    }
}
