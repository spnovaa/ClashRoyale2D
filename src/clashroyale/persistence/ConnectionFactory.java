package clashroyale.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hands out short-lived JDBC {@link Connection} instances to be used
 * inside try-with-resources blocks.
 *
 * <p>Replaces the previous {@code DbConnect} that swallowed exceptions
 * with {@code printStackTrace()} and stored the connection on the
 * instance, then leaked it forever because it was never closed.</p>
 *
 * <p>Connection-pooling concerns (HikariCP, c3p0, …) can be slotted in
 * here without touching any caller — this is the seam.</p>
 */
public final class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC driver missing from classpath", e);
        }
    }

    private ConnectionFactory() {}

    /**
     * @return a fresh, open JDBC connection. Callers <strong>must</strong>
     *         close it (typically via try-with-resources).
     */
    public static Connection open() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.url(),
                DatabaseConfig.username(),
                DatabaseConfig.password()
        );
    }
}
