package clashroyale.models;

import clashroyale.models.enums.BotKind;
import clashroyale.persistence.ConnectionFactory;
import clashroyale.security.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Authentication model.
 *
 * <p>Hardened version of the original:</p>
 * <ul>
 *   <li>SQL injection fixed — every query uses {@link PreparedStatement}.</li>
 *   <li>Connections / statements / result-sets close deterministically via
 *       try-with-resources.</li>
 *   <li>Password storage is BCrypt-hashed; legacy plaintext rows are
 *       transparently upgraded on first successful login.</li>
 *   <li>Return-code constants are named instead of magic numbers.</li>
 * </ul>
 *
 * <p>Public method signatures are preserved so existing controllers
 * keep compiling.</p>
 */
public class LoginModel {

    public static final int RESULT_LOGGED_IN          = 1;
    public static final int RESULT_USERNAME_NOT_FOUND = 0;
    public static final int RESULT_WRONG_PASSWORD     = 2;
    public static final int RESULT_CONNECTION_FAILED  = -1;

    public static final int RESULT_REGISTERED         = 1;
    public static final int RESULT_REGISTER_FAILED    = 0;

    private static final Logger LOGGER = Logger.getLogger(LoginModel.class.getName());

    /**
     * @return one of the {@code RESULT_*} constants above.
     */
    public int tryToLogin(String username, String password, UserModel userModel) {
        final String sql = "SELECT id, password, level, bot_type FROM users WHERE username = ?";
        try (Connection con = ConnectionFactory.open();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return RESULT_USERNAME_NOT_FOUND;
                }

                String storedHash = rs.getString("password");
                if (!PasswordHasher.verify(password, storedHash)) {
                    return RESULT_WRONG_PASSWORD;
                }

                userModel.setUsername(username);
                userModel.setLevel(parseIntOrZero(rs.getString("level")));
                userModel.setBotType(BotKind.fromLegacyDbValue(parseIntOrZero(rs.getString("bot_type"))).legacyId());
                userModel.setId(rs.getString("id"));

                if (!PasswordHasher.isBcrypt(storedHash)) {
                    upgradePasswordHash(con, username, password);
                }
                return RESULT_LOGGED_IN;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Login query failed", e);
            return RESULT_CONNECTION_FAILED;
        }
    }

    public int tryToRegister(String username, String password) {
        final String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
        try (Connection con = ConnectionFactory.open();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, PasswordHasher.hash(password));
            int rows = stmt.executeUpdate();
            return rows > 0 ? RESULT_REGISTERED : RESULT_REGISTER_FAILED;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Registration failed", e);
            return RESULT_CONNECTION_FAILED;
        }
    }

    private void upgradePasswordHash(Connection con, String username, String password) {
        final String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, PasswordHasher.hash(password));
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Failed to upgrade legacy password hash", e);
        }
    }

    private static int parseIntOrZero(String value) {
        if (value == null) return 0;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
