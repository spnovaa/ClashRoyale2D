package clashroyale.models;

import java.sql.*;

/**
 * The type Login model.
 */
public class LoginModel {
    /**
     * Try to login username and password.
     *
     * @param username the username
     * @param password the password
     * @return 2 if password is incorrect, 1 if logged in successfully, 0 if username not found, -1 if connection failed
     */
    public int tryToLogin(String username, String password, UserModel userModel) {
        try {
            Connection con = new DbConnect().getConnection();
            if (con == null) throw new SQLException("CONNECTION FAILED!");
            Statement statement = con.createStatement();
            String query = "SELECT * FROM users WHERE username ='" + username + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    userModel.setUsername(username);
                    userModel.setId(resultSet.getString("id"));
                    return 1;
                } else
                    return 2;
            } else return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Try to register username and password.
     *
     * @param username the username
     * @param password the password
     * @return 1 if successful, 0 if query error, -1 if connection failed
     */
    public int tryToRegister(String username, String password) {
        try {
            Connection con = new DbConnect().getConnection();
            if (con == null) throw new SQLException("CONNECTION FAILED");
            String insertion = "insert into users(username,password) VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(insertion);
            st.setString(1, username);
            st.setString(2, password);
            int res = st.executeUpdate();
            return (res > 0 ? 1 : 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
