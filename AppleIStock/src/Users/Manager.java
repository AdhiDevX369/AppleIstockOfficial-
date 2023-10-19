package Users;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Manager extends User {

    public Manager(int userId, String username, String password) {
        super(userId, username, password, "Manager");
    }

    public void createAccount(String newUsername, String newPassword) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Check if the username is unique (assuming 'username' is unique in the 'user' table)
            String checkQuery = "SELECT * FROM user WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, newUsername);

            // If the username is not already in use, create the account
            if (!checkStatement.executeQuery().next()) {
                String insertQuery = "INSERT INTO user (username, password, position) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setString(3, "Cashier");

                int rowsAffected = preparedStatement.executeUpdate();

                preparedStatement.close();

                if (rowsAffected > 0) {
                    System.out.println("Account created successfully.");
                } else {
                    System.out.println("Account creation failed.");
                }
            } else {
                System.out.println("Username is already in use.");
            }

            checkStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewProduct(String productName, double price, String category) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String query = "INSERT INTO product (product_name, price, category) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, category);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(String productName, double newPrice) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String query = "UPDATE product SET price = ? WHERE product_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, productName);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void analyzeSales() {
        // Implementation to analyze sales
    }

    public void searchProduct(String keyword) {
        // Implementation for product search
    }
}
