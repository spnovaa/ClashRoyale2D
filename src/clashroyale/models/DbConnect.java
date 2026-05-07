package clashroyale.models;

import clashroyale.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backwards-compatible shim around {@link ConnectionFactory}.
 *
 * <p>Existing call sites (e.g. {@code UserModel#getChosenCardsFromDB()})
 * still construct a {@code DbConnect} and call {@code getConnection()};
 * this delegates to the modern factory so credentials no longer live
 * in source and connection setup is in one place.</p>
 *
 * @deprecated New code should depend on {@link ConnectionFactory#open()}
 *             directly inside try-with-resources blocks.
 */
@Deprecated
public class DbConnect {

    private static final Logger LOGGER = Logger.getLogger(DbConnect.class.getName());

    public Connection getConnection() {
        try {
            return ConnectionFactory.open();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to open database connection", e);
            return null;
        }
    }
}
