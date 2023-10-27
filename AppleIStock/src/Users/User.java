package Users;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int userId;
    private String username;
    private String password;
    private String position;

    public User(int userId, String username, String password, String position) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.position = position;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    // Database methods
    public static User authenticateUser(String username, String password) {
        User authenticatedUser = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("userid");
                String userPosition = resultSet.getString("position");

                if ("Cashier".equals(userPosition)) {
                    authenticatedUser = new Cashier(userId, username, password);
                } else if ("Manager".equals(userPosition)) {
                    authenticatedUser = new Manager(userId, username, password);
                }
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authenticatedUser;
    }
}
