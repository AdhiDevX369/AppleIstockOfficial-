/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Controllers.CashierController;
import Controllers.DBController;
import Controllers.ManagerController;
import Models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adithya
 */
public class UserDAO {

    private DBController dbController;

    public UserDAO() {
       dbController = DBController.getInstance(); // Assuming you have a DatabaseController class
    }

    public User authenticateUser(String username, String password) {
        User authenticatedUser = null;
        String query = "SELECT username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "userid, position, mobile, email, nic FROM Users "
                + "WHERE username = ? OR password = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("userid");
                    String userPosition = resultSet.getString("position");
                    String name = resultSet.getString("fullname");
                    String address = resultSet.getString("address");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("mobile");
                    String nic = resultSet.getString("nic");
                    
                    if ("Cashier".equals(userPosition)) {
                        authenticatedUser = new CashierController(userId, username, password, name, address, email, phone, nic);
                    } else if ("Manager".equals(userPosition)) {
                        authenticatedUser = new ManagerController(userId, username, password, name, address, email, phone, nic);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return authenticatedUser;
    }

    public boolean createUser(String username, String password, String firstName, String lastName, String mobile,
            String email, String street, String city, String country, String position, String nic) {
        String query = "INSERT INTO Users (username, password, firstName, lastName, mobile, email, street, city, country, position, nic) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, mobile);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, street);
            preparedStatement.setString(8, city);
            preparedStatement.setString(9, country);
            preparedStatement.setString(10, position);
            preparedStatement.setString(11, nic);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user created)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public List<String> searchUserData(String keyword) {
        List<String> userData = new ArrayList<>();
        String query = "SELECT username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "mobile, email, nic FROM Users "
                + "WHERE username = ? OR firstName = ? OR lastName = ? OR street = ?OR city = ?OR country = ? OR email = ? OR mobile = ? OR nic = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 1; i <= 9; i++) {
                preparedStatement.setString(i, keyword);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userData.add(resultSet.getString("username"));
                userData.add(resultSet.getString("password"));
                userData.add(resultSet.getString("fullname"));
                userData.add(resultSet.getString("address"));
                userData.add(resultSet.getString("mobile"));
                userData.add(resultSet.getString("email"));
                userData.add(resultSet.getString("nic"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return userData;
    }

    public List<List<String>> viewAllUserData() {
        List<List<String>> allUserData = new ArrayList<>(); // List to hold data for all users
        String query = "SELECT username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "mobile, email, nic FROM Users";

        try (Connection connection = dbController.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                List<String> userData = new ArrayList<>();
                userData.add(resultSet.getString("username"));
                userData.add(resultSet.getString("password"));
                userData.add(resultSet.getString("fullname"));
                userData.add(resultSet.getString("address"));
                userData.add(resultSet.getString("mobile"));
                userData.add(resultSet.getString("email"));
                userData.add(resultSet.getString("nic"));
                allUserData.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return allUserData;
    }

    public boolean updateUser(String username, String newPassword, String newFirstName, String newLastName,
            String newMobile, String newEmail, String newStreet, String newCity,
            String newCountry, String newNic) {
        String query = "UPDATE Users SET password = ?, firstName = ?, lastName = ?, "
                + "mobile = ?, email = ?, street = ?, city = ?, country = ?, nic = ? "
                + "WHERE username = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newFirstName);
            preparedStatement.setString(3, newLastName);
            preparedStatement.setString(4, newMobile);
            preparedStatement.setString(5, newEmail);
            preparedStatement.setString(6, newStreet);
            preparedStatement.setString(7, newCity);
            preparedStatement.setString(8, newCountry);
            preparedStatement.setString(9, newNic);
            preparedStatement.setString(10, username);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user updated)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public boolean deleteUser(int userid) {
        String query = "DELETE FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user deleted)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    // Other methods for CRUD operations on Users
}
