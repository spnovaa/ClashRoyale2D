package clashroyale.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Db connect.
 */
public class DbConnect {
    private Connection connection;

    /**
     * Instantiates a new Db connect.
     */
    public DbConnect() {
        connection = null;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String URL = "jdbc:mysql://localhost:3306/clashroyale2d";
        String dbUsername = "root";
        String dbPassword = "";
        try {
            connection = DriverManager.getConnection(URL, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }
}
