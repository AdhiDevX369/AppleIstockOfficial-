package Controller;

// Import for managing database connections
import java.sql.Connection;

// Import for loading database drivers and establishing connections
import java.sql.DriverManager;

// Import for handling exceptions related to database operations
import java.sql.SQLException;

public class DatabaseController {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/applestock";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ABandara2001";

    private Connection connection;

    // Singleton pattern to ensure a single database connection
    private static DatabaseController instance;

    private DatabaseController() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
