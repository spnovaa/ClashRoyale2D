package clashroyale.persistence;

/**
 * Connection settings for the application's MySQL database, sourced
 * from environment variables (or the supplied defaults).
 *
 * <p>Eliminates the previous practice of hard-coding the username
 * "{@code root}" and an empty password directly in source — values
 * that should never live in version control.</p>
 */
public final class DatabaseConfig {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/clashroyale2d?useSSL=false&serverTimezone=UTC";

    public static String url() {
        return env("CR2D_DB_URL", DEFAULT_URL);
    }

    public static String username() {
        return env("CR2D_DB_USERNAME", "root");
    }

    public static String password() {
        return env("CR2D_DB_PASSWORD", "");
    }

    private DatabaseConfig() {}

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
