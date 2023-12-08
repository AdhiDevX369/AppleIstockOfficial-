/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/stock";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ABandara2001";
    private Connection connection;
    private static DBController instance;

    private DBController() {
        // Initialize the database connection upon instantiation
        establishConnection();
    }

    private void establishConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failures appropriately
        }
    }

    public static synchronized DBController getInstance() {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                establishConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection closure exception
        }
        return connection;
    }

    // Other methods for handling transactions, commit, rollback, etc.
}
