package Models;

// Import necessary controllers to manage different parts of the application
import Controller.DatabaseController; // DatabaseController manages database connections
import Controller.CashierController; // CashierController handles operations related to cashiers
import Controller.ManagerController; // ManagerController manages manager-related functions

// Import JDBC classes for database operations
import java.sql.Connection; // Connection is used to establish a connection to the database
import java.sql.PreparedStatement; // PreparedStatement is used for executing parameterized SQL queries
import java.sql.ResultSet; // ResultSet stores the results of a database query
import java.sql.SQLException; // SQLException is used to handle database-related errors

public class User {

    private int userId;
    private String username;
    private String password;
    private String position;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String nic;

    // Constructor to initialize user information
    public User(int userId, String username, String password, String position, String name, String address, String email, String phone, String nic) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.position = position;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
    }

    // Getter methods to retrieve user information
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

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNic() {
        return nic;
    }

    // Database methods
    // Authenticate user based on username and password
    public static User authenticateUser(String username, String password) {
        User authenticatedUser = null;
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("userid");
                String userPosition = resultSet.getString("position");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String nic = resultSet.getString("nic");

                if ("Cashier".equals(userPosition)) {
                    authenticatedUser = new CashierController(userId, username, password, name, address, email, phone, nic);
                } else if ("Manager".equals(userPosition)) {
                    authenticatedUser = new ManagerController(userId, username, password, name, address, email, phone, nic);
                }
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authenticatedUser;
    }

    // Check if a username already exists in the database
    public static boolean authenticateUsername(String username) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean usernameExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return usernameExists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if a username and password combination is valid
    public static boolean authenticatePassword(String username, String password) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean validPassword = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return validPassword;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
